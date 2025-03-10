package scalapb.compiler

import com.google.protobuf.DescriptorProtos.{
  DescriptorProto,
  EnumDescriptorProto,
  FileDescriptorProto,
  ServiceDescriptorProto,
  SourceCodeInfo
}
import com.google.protobuf.Descriptors._
import com.google.protobuf.WireFormat.FieldType
import scalapb.options.compiler.Scalapb
import scalapb.options.compiler.Scalapb._

import scala.collection.JavaConverters._
import scala.collection.immutable.IndexedSeq

class DescriptorImplicits(params: GeneratorParams, files: Seq[FileDescriptor]) {
  import DescriptorImplicits._
  val SCALA_RESERVED_WORDS = Set(
    "abstract",
    "case",
    "catch",
    "class",
    "def",
    "do",
    "else",
    "extends",
    "false",
    "final",
    "finally",
    "for",
    "forSome",
    "if",
    "implicit",
    "import",
    "lazy",
    "macro",
    "match",
    "new",
    "null",
    "object",
    "override",
    "package",
    "private",
    "protected",
    "return",
    "sealed",
    "super",
    "then",
    "this",
    "throw",
    "trait",
    "try",
    "true",
    "type",
    "val",
    "var",
    "while",
    "with",
    "yield",
    "ne"
  )

  // Needs to be lazy since the input may be invalid... For example, if one of
  // the cases is not a message, the call to getMessageType would fail.
  private lazy val sealedOneofsCache: SealedOneofsCache = {
    val sealedOneof = for {
      file    <- files
      message <- file.allMessages if message.isSealedOneofType
    } yield
      SealedOneof(
        message,
        message.getOneofs.get(0).getFields.asScala.map(_.getMessageType).toVector
      )
    new SealedOneofsCache(sealedOneof)
  }

  private lazy val fileOptionsCache = FileOptionsCache.buildCache(files)

  implicit class AsSymbolPimp(val s: String) {
    def asSymbol: String = if (SCALA_RESERVED_WORDS.contains(s)) s"`$s`" else s
  }

  implicit final class MethodDescriptorPimp(method: MethodDescriptor) {

    class MethodTypeWrapper(descriptor: Descriptor) {
      def customScalaType =
        if (descriptor.isSealedOneofType)
          Some(descriptor.sealedOneofScalaType)
        else None

      def baseScalaType = descriptor.scalaTypeNameWithMaybeRoot

      def scalaType = customScalaType.getOrElse(baseScalaType)
    }

    def inputType = new MethodTypeWrapper(method.getInputType)

    def outputType = new MethodTypeWrapper(method.getOutputType)

    def isClientStreaming = method.toProto.getClientStreaming

    def isServerStreaming = method.toProto.getServerStreaming

    def streamType: StreamType = {
      (isClientStreaming, isServerStreaming) match {
        case (false, false) => StreamType.Unary
        case (true, false)  => StreamType.ClientStreaming
        case (false, true)  => StreamType.ServerStreaming
        case (true, true)   => StreamType.Bidirectional
      }
    }

    def canBeBlocking = !method.toProto.getClientStreaming

    private def name0: String = NameUtils.snakeCaseToCamelCase(method.getName)

    def name: String = name0.asSymbol

    def descriptorName = s"METHOD_${NameUtils.toAllCaps(method.getName)}"

    def sourcePath: Seq[Int] = {
      method.getService.sourcePath ++ Seq(
        ServiceDescriptorProto.METHOD_FIELD_NUMBER,
        method.getIndex
      )
    }

    def comment: Option[String] = {
      method.getFile
        .findLocationByPath(sourcePath)
        .map(t => t.getLeadingComments + t.getTrailingComments)
        .map(Helper.escapeComment)
        .filter(_.nonEmpty)
    }

    def deprecatedAnnotation: String = {
      if (method.getOptions.getDeprecated) {
        ProtobufGenerator.deprecatedAnnotation + " "
      } else {
        ""
      }
    }
  }

  implicit final class ServiceDescriptorPimp(self: ServiceDescriptor) {
    def objectName = self.getName + "Grpc"

    def name = self.getName.asSymbol

    def blockingClient = self.getName + "BlockingClient"

    def blockingStub = self.getName + "BlockingStub"

    def stub = self.getName + "Stub"

    def methods = self.getMethods.asScala.toIndexedSeq

    def descriptorName = "SERVICE"

    def scalaDescriptorSource: String =
      s"${self.getFile.fileDescriptorObjectName}.scalaDescriptor.services(${self.getIndex})"

    def sourcePath: Seq[Int] = Seq(FileDescriptorProto.SERVICE_FIELD_NUMBER, self.getIndex)

    def comment: Option[String] = {
      self.getFile
        .findLocationByPath(sourcePath)
        .map(t => t.getLeadingComments + t.getTrailingComments)
        .map(Helper.escapeComment)
        .filter(_.nonEmpty)
    }

    def deprecatedAnnotation: String = {
      if (self.getOptions.getDeprecated) {
        ProtobufGenerator.deprecatedAnnotation + " "
      } else {
        ""
      }
    }
  }

  implicit class FieldDescriptorPimp(val fd: FieldDescriptor) {
    import NameUtils._

    def containingOneOf: Option[OneofDescriptor] = Option(fd.getContainingOneof)

    def isInOneof: Boolean = containingOneOf.isDefined

    def isSealedOneofType: Boolean = fd.isMessage && fd.getMessageType.isSealedOneofType

    def scalaName: String =
      if (fieldOptions.getScalaName.nonEmpty) fieldOptions.getScalaName
      else
        fd.getName match {
          case ("number" | "value") if fd.isInOneof => "_" + fd.getName
          case "serialized_size"                    => "_serializedSize"
          case x =>
            getNameWithFallback(x, Case.CamelCase, Appendage.Prefix)
        }

    def upperScalaName: String =
      if (fieldOptions.getScalaName.nonEmpty)
        snakeCaseToCamelCase(fieldOptions.getScalaName, true)
      else
        fd.getName match {
          case "serialized_size" => "_SerializedSize"
          case "class"           => "_Class"
          case x                 => getNameWithFallback(x, Case.PascalCase, Appendage.Prefix)
        }

    private def getNameWithFallback(x: String, targetCase: Case, appendage: Appendage) = {
      val candidate = snakeCaseToCamelCase(x, targetCase.isPascal)
      if (ProtoValidation.ForbiddenFieldNames.contains(candidate)) {
        if (appendage.isPrefix) "_" + candidate
        else candidate + "_"
      } else candidate
    }

    def upperJavaName: String = fd.getName match {
      case "serialized_size" => "SerializedSize_"
      case "class"           => "Class_"
      case x                 => getNameWithFallback(x, Case.PascalCase, Appendage.Postfix)
    }

    def fieldNumberConstantName: String = fd.getName.toUpperCase() + "_FIELD_NUMBER"

    def oneOfTypeName = {
      assert(isInOneof)
      fd.getContainingOneof.scalaTypeName + "." + upperScalaName
    }

    def noBox =
      if (fieldOptions.hasNoBox) fieldOptions.getNoBox
      else if (fd.isMessage) fd.getMessageType.noBox
      else false

    // Is this field boxed inside an Option in Scala. Equivalent, does the Java API
    // support hasX methods for this field.
    def supportsPresence: Boolean =
      fd.isOptional && !fd.isInOneof && (!fd.getFile.isProto3 || fd.isMessage) &&
        !noBox && !fd.isSealedOneofType

    // Is the Scala representation of this field a singular type.
    def isSingular =
      fd.isRequired || (fd.getFile.isProto3 && !fd.isInOneof && fd.isOptional && !fd.isMessage) || (
        fd.isOptional && (noBox || (fd.isSealedOneofType && !fd.isInOneof))
      )

    def enclosingType: EnclosingType =
      if (isSingular) EnclosingType.None
      else if (supportsPresence || fd.isInOneof) EnclosingType.ScalaOption
      else {
        EnclosingType.Collection(collectionType)
      }

    def fieldMapEnclosingType: EnclosingType =
      if (isSingular) EnclosingType.None
      else if (supportsPresence || fd.isInOneof) EnclosingType.ScalaOption
      else if (!fd.isMapField) EnclosingType.Collection(collectionType)
      else EnclosingType.Collection(ScalaSeq)

    def isMapField = isMessage && fd.isRepeated && fd.getMessageType.isMapEntry

    def mapType: MessageDescriptorPimp#MapType = {
      assert(isMapField)
      fd.getMessageType.mapType
    }

    def collectionBuilder: String = {
      require(fd.isRepeated)
      val t = if (collectionType == ScalaSeq) ScalaVector else collectionType

      if (!fd.isMapField)
        s"$t.newBuilder[$singleScalaTypeName]"
      else {
        s"$t.newBuilder[${fd.mapType.keyType}, ${fd.mapType.valueType}]"
      }
    }

    def emptyCollection: String = {
      s"${collectionType}.empty"
    }

    // In scalapb.proto, we separate between collection_type and map_type, but internally this is unified.
    def collectionType: String = {
      require(fd.isRepeated)
      if (fd.isMapField) {
        if (fd.fieldOptions.hasMapType) fd.fieldOptions.getMapType
        else if (fd.getFile.scalaOptions.hasMapType) fd.getFile.scalaOptions.getMapType
        else ScalaMap
      } else {
        if (fd.fieldOptions.hasCollectionType) fd.fieldOptions.getCollectionType
        else if (fd.getFile.scalaOptions.hasCollectionType)
          fd.getFile.scalaOptions.getCollectionType
        else ScalaSeq
      }
    }

    def fieldMapCollection(innerType: String) = {
      if (supportsPresence) s"_root_.scala.Option[$innerType]"
      else if (fd.isRepeated && !fd.isMapField) s"${collectionType}[$innerType]"
      else if (fd.isRepeated && fd.isMapField) s"${ScalaSeq}[$innerType]"
      else innerType
    }

    def fieldsMapEmptyCollection: String = {
      require(fd.isRepeated)
      if (fd.isMapField) s"$ScalaSeq.empty"
      else emptyCollection
    }

    def scalaTypeName: String =
      if (fd.isMapField) {
        s"$collectionType[${mapType.keyType}, ${mapType.valueType}]"
      } else if (fd.isRepeated) s"${collectionType}[$singleScalaTypeName]"
      else if (supportsPresence) s"${ScalaOption}[$singleScalaTypeName]"
      else singleScalaTypeName

    def fieldOptions: FieldOptions = fd.getOptions.getExtension[FieldOptions](Scalapb.field)

    def annotationList: Seq[String] = {
      val deprecated = {
        if (fd.getOptions.getDeprecated)
          List(ProtobufGenerator.deprecatedAnnotation)
        else
          Nil
      }
      deprecated ++ fieldOptions.getAnnotationsList().asScala.toSeq
    }

    def customSingleScalaTypeName: Option[String] = {
      // If the current message is within a MapEntry (that is a key, or a value), find the actual map
      // field in the enclosing message. This is used to determine map level options when processing the
      // key and value fields.
      def fieldReferencingMap: FieldDescriptor = {
        require(fd.getContainingType.isMapEntry)
        val messageReferencingMap = fd.getContainingType.getContainingType
        messageReferencingMap.getFields.asScala
          .filter(_.isMapField)
          .find(fd.getContainingType eq _.getMessageType)
          .get
      }

      if (isMapField) Some(s"(${mapType.keyType}, ${mapType.valueType})")
      else if (isSealedOneofType) Some(fd.getMessageType.sealedOneofScalaType)
      else if (fieldOptions.hasType) Some(fieldOptions.getType)
      else if (isMessage && fd.getMessageType.messageOptions.hasType)
        Some(fd.getMessageType.messageOptions.getType)
      else if (isEnum && fd.getEnumType.scalaOptions.hasType)
        Some(fd.getEnumType.scalaOptions.getType)
      else if (fd.getContainingType.isMapEntry && fd.getNumber == 1 && fieldReferencingMap.fieldOptions.hasKeyType)
        Some(fieldReferencingMap.fieldOptions.getKeyType)
      else if (fd.getContainingType.isMapEntry && fd.getNumber == 2 && fieldReferencingMap.fieldOptions.hasValueType)
        Some(fieldReferencingMap.fieldOptions.getValueType)
      else if (isMessage && fd.getFile.usePrimitiveWrappers) (fd.getMessageType.getFullName match {
        case "google.protobuf.Int32Value"  => Some("_root_.scala.Int")
        case "google.protobuf.Int64Value"  => Some("_root_.scala.Long")
        case "google.protobuf.UInt32Value" => Some("_root_.scala.Int")
        case "google.protobuf.UInt64Value" => Some("_root_.scala.Long")
        case "google.protobuf.DoubleValue" => Some("_root_.scala.Double")
        case "google.protobuf.FloatValue"  => Some("_root_.scala.Float")
        case "google.protobuf.StringValue" => Some("_root_.scala.Predef.String")
        case "google.protobuf.BoolValue"   => Some("_root_.scala.Boolean")
        case "google.protobuf.BytesValue"  => Some("_root_.com.google.protobuf.ByteString")
        case _                             => None
      })
      else None
    }

    def baseSingleScalaTypeName: String = fd.getJavaType match {
      case FieldDescriptor.JavaType.INT         => "_root_.scala.Int"
      case FieldDescriptor.JavaType.LONG        => "_root_.scala.Long"
      case FieldDescriptor.JavaType.FLOAT       => "_root_.scala.Float"
      case FieldDescriptor.JavaType.DOUBLE      => "_root_.scala.Double"
      case FieldDescriptor.JavaType.BOOLEAN     => "_root_.scala.Boolean"
      case FieldDescriptor.JavaType.BYTE_STRING => "_root_.com.google.protobuf.ByteString"
      case FieldDescriptor.JavaType.STRING      => "_root_.scala.Predef.String"
      case FieldDescriptor.JavaType.MESSAGE =>
        fd.getMessageType.scalaTypeNameWithMaybeRoot(fd.getContainingType)
      case FieldDescriptor.JavaType.ENUM =>
        fd.getEnumType.scalaTypeNameWithMaybeRoot(fd.getContainingType)
    }

    def singleScalaTypeName = customSingleScalaTypeName.getOrElse(baseSingleScalaTypeName)

    def getMethod = "get" + upperScalaName

    def typeMapperValName = "_typemapper_" + scalaName

    def typeMapper = {
      if (!fd.isExtension)
        fd.getContainingType.scalaTypeName + "." + typeMapperValName
      else {
        val c =
          if (fd.getExtensionScope == null) fd.getFile.fileDescriptorObjectFullName
          else fd.getExtensionScope.scalaTypeName
        c + "." + typeMapperValName
      }
    }

    def isEnum = fd.getType == FieldDescriptor.Type.ENUM

    def isMessage = fd.getType == FieldDescriptor.Type.MESSAGE

    def javaExtensionFieldFullName = {
      require(fd.isExtension)
      val inClass =
        if (fd.getExtensionScope == null) fd.getFile.javaFullOuterClassName
        else fd.getExtensionScope.javaTypeName
      s"$inClass.${fd.scalaName}"
    }

    def sourcePath: Seq[Int] = {
      fd.getContainingType.sourcePath ++ Seq(DescriptorProto.FIELD_FIELD_NUMBER, fd.getIndex)
    }

    def comment: Option[String] = {
      fd.getFile
        .findLocationByPath(sourcePath)
        .map(t => t.getLeadingComments + t.getTrailingComments)
        .map(Helper.escapeComment)
        .filter(_.nonEmpty)
    }
  }

  implicit class OneofDescriptorPimp(val oneof: OneofDescriptor) {

    def javaEnumName = {
      val name = NameUtils.snakeCaseToCamelCase(oneof.getName, true)
      s"get${name}Case"
    }

    def scalaName = NameUtils.snakeCaseToCamelCase(oneof.getName)

    def upperScalaName = {
      val name = oneof.getName match {
        case "ValueType" | "value_type" => "ValueTypeOneof"
        case n                          => n
      }
      NameUtils.snakeCaseToCamelCase(name, true)
    }

    def fields: IndexedSeq[FieldDescriptor] =
      (0 until oneof.getFieldCount).map(oneof.getField).filter(_.getLiteType != FieldType.GROUP)

    def scalaTypeName = oneof.getContainingType.scalaTypeName + "." + upperScalaName

    def empty = scalaTypeName + ".Empty"

    def oneofOptions: OneofOptions = oneof.getOptions.getExtension[OneofOptions](Scalapb.oneof)

    def baseClasses = "_root_.scalapb.GeneratedOneof" +: oneofOptions.getExtendsList.asScala.toSeq
  }

  private val OneofMessageSuffix = "Message"

  implicit class MessageDescriptorPimp(val message: Descriptor) {

    def fields = message.getFields.asScala.filter(_.getLiteType != FieldType.GROUP).toSeq

    def fieldsWithoutOneofs = fields.filterNot(_.isInOneof)

    def parent: Option[Descriptor] = Option(message.getContainingType)

    // every message that passes this filter must be a sealed oneof. The check that it actually
    // obeys the rules is done in ProtoValidation.
    def isSealedOneofType: Boolean = {
      message.getOneofs.asScala.exists(_.getName == "sealed_value")
    }

    def isSealedOneofCase: Boolean = sealedOneofsCache.getContainer(message).isDefined

    def scalaName: String = message.getName match {
      case "Option" => "OptionProto"
      case name =>
        if (message.isSealedOneofType) name + OneofMessageSuffix
        else name
    }

    lazy val scalaTypeName: String = parent match {
      case Some(p) => p.scalaTypeName + "." + nameSymbol
      case None    => (message.getFile.scalaPackagePartsAsSymbols :+ nameSymbol).mkString(".")
    }

    // When the first component of the package name is the same as one of the fields in the
    // current context, we need to disambiguate or we get a compile error.
    def scalaTypeNameWithMaybeRoot(context: Descriptor): String = {
      val fullName        = scalaTypeName
      val topLevelPackage = fullName.split('.')(0)
      if (context.fields
            .map(_.scalaName)
            .contains(topLevelPackage) && !message.getFile.scalaPackageName.isEmpty)
        s"_root_.$fullName"
      else fullName
    }

    def scalaTypeNameWithMaybeRoot: String = {
      val fullName        = scalaTypeName
      val topLevelPackage = fullName.split('.')(0)
      val ConflictingNames = Seq(
        "build" // Grpc stubs have a method build, so we need _root_ to disambiguate from that.
      )
      if (!message.getFile.scalaPackageName.isEmpty || ConflictingNames.contains(topLevelPackage))
        s"_root_.$fullName"
      else fullName
    }

    private[compiler] def hasConflictingJavaClassName(className: String): Boolean =
      ((message.getName == className) ||
        (message.getEnumTypes.asScala.exists(_.getName == className)) ||
        (message.nestedTypes.exists(_.hasConflictingJavaClassName(className))))

    def javaTypeName = message.getFile.fullJavaName(message.getFullName)

    def messageOptions: MessageOptions =
      message.getOptions.getExtension[MessageOptions](Scalapb.message)

    def noBox = message.messageOptions.getNoBox

    private[this] def deprecatedAnnotation: Seq[String] = {
      if (message.getOptions.getDeprecated)
        List(ProtobufGenerator.deprecatedAnnotation)
      else
        Nil
    }

    def annotationList: Seq[String] = {
      deprecatedAnnotation ++ messageOptions.getAnnotationsList().asScala
    }

    def companionAnnotationList: Seq[String] = {
      deprecatedAnnotation ++ message.messageOptions.getCompanionAnnotationsList().asScala
    }

    def extendsOption = messageOptions.getExtendsList.asScala.filterNot(valueClassNames).toSeq

    def companionExtendsOption = messageOptions.getCompanionExtendsList.asScala.toSeq

    def sealedOneofExtendsOption = messageOptions.getSealedOneofExtendsList.asScala.toSeq

    def sealedOneOfExtendsCount = messageOptions.getSealedOneofExtendsCount

    def nameSymbol = scalaName.asSymbol

    def sealedOneofName = {
      require(isSealedOneofType)
      scalaName.stripSuffix(OneofMessageSuffix).asSymbol
    }

    def sealedOneofNameSymbol = {
      sealedOneofName.asSymbol
    }

    def sealedOneofScalaType = {
      parent match {
        case Some(p) => p.scalaTypeName + "." + sealedOneofNameSymbol
        case None =>
          (message.getFile.scalaPackagePartsAsSymbols :+ sealedOneofNameSymbol).mkString(".")
      }
    }

    private[this] val valueClassNames = Set("AnyVal", "scala.AnyVal", "_root_.scala.AnyVal")

    def isValueClass: Boolean = messageOptions.getExtendsList.asScala.exists(valueClassNames)

    // In protobuf 3.5.0 all messages preserve unknown fields. We make an exception for value classes
    // since they must have an exactly one val.
    def preservesUnknownFields =
      (
        message.isExtendable || message.getFile.scalaOptions.getPreserveUnknownFields
      ) && !isValueClass

    def sealedOneofContainer: Option[Descriptor] =
      sealedOneofsCache.getContainer(message)

    def sealedOneofCases: Option[Seq[Descriptor]] =
      sealedOneofsCache.getCases(message)

    def generateLenses: Boolean =
      if (message.getFile.scalaOptions.hasLenses)
        (message.getFile.scalaOptions.getLenses)
      else params.lenses

    def baseClasses: Seq[String] = {
      val specialMixins = message.getFullName match {
        case "google.protobuf.Any" => Seq("_root_.scalapb.AnyMethods")
        case _                     => Seq()
      }

      val extendable =
        if (message.isExtendable) Seq(s"_root_.scalapb.ExtendableMessage[$nameSymbol]") else Nil

      val anyVal = if (isValueClass) Seq("AnyVal") else Nil

      val sealedOneofTrait = sealedOneofContainer match {
        case Some(parent) => List(parent.sealedOneofScalaType)
        case _            => List()
      }

      anyVal ++ sealedOneofTrait ++ Seq(
        "scalapb.GeneratedMessage",
        s"scalapb.Message[$nameSymbol]",
        s"scalapb.lenses.Updatable[$nameSymbol]"
      ) ++ extendable ++ extendsOption ++ specialMixins
    }

    def companionBaseClasses: Seq[String] = {
      val mixins =
        if (javaConversions)
          Seq(s"scalapb.JavaProtoSupport[$scalaTypeName, $javaTypeName]")
        else Nil

      val specialMixins = message.getFullName match {
        case "google.protobuf.Any" => Seq("scalapb.AnyCompanionMethods")
        case _                     => Seq()
      }

      Seq(s"scalapb.GeneratedMessageCompanion[$scalaTypeName]") ++
        mixins ++
        companionExtendsOption ++
        specialMixins
    }

    def sealedOneofBaseClasses: Seq[String] =
      s"scalapb.GeneratedSealedOneof" +: messageOptions.getSealedOneofExtendsList.asScala.toSeq

    def nestedTypes: Seq[Descriptor] = message.getNestedTypes.asScala.toSeq

    def isMapEntry: Boolean = message.getOptions.getMapEntry

    def javaConversions = message.getFile.javaConversions && !isMapEntry

    def isTopLevel = message.getContainingType == null

    class MapType {
      def keyField = message.findFieldByName("key")

      def keyType = keyField.singleScalaTypeName

      def valueField = message.findFieldByName("value")

      def valueType = valueField.singleScalaTypeName

      def pairType = s"($keyType, $valueType)"
    }

    def mapType: MapType = {
      assert(message.isMapEntry)
      new MapType
    }

    def javaDescriptorSource: String =
      if (message.isTopLevel)
        s"${message.getFile.fileDescriptorObjectName}.javaDescriptor.getMessageTypes.get(${message.getIndex})"
      else
        s"${message.getContainingType.scalaTypeName}.javaDescriptor.getNestedTypes.get(${message.getIndex})"

    def scalaDescriptorSource: String =
      if (message.isTopLevel)
        s"${message.getFile.fileDescriptorObjectName}.scalaDescriptor.messages(${message.getIndex})"
      else
        s"${message.getContainingType.scalaTypeName}.scalaDescriptor.nestedMessages(${message.getIndex})"

    def sourcePath: Seq[Int] = {
      if (message.isTopLevel) Seq(FileDescriptorProto.MESSAGE_TYPE_FIELD_NUMBER, message.getIndex)
      else
        message.getContainingType.sourcePath ++ Seq(
          DescriptorProto.NESTED_TYPE_FIELD_NUMBER,
          message.getIndex
        )
    }

    def comment: Option[String] = {
      message.getFile
        .findLocationByPath(sourcePath)
        .map(t => t.getLeadingComments + t.getTrailingComments)
        .map(Helper.escapeComment)
        .filter(_.nonEmpty)
    }
  }

  implicit class EnumDescriptorPimp(val `enum`: EnumDescriptor) {
    def parentMessage: Option[Descriptor] = Option(`enum`.getContainingType)

    def scalaOptions: EnumOptions = `enum`.getOptions.getExtension[EnumOptions](Scalapb.enumOptions)

    def name: String = `enum`.getName match {
      case "Option" => "OptionEnum"
      case "ValueType" =>
        "ValueTypeEnum" // Issue 348, conflicts with "type ValueType" in GeneratedEnumCompanion.
      case n => n
    }

    def nameSymbol = name.asSymbol

    lazy val scalaTypeName: String = parentMessage match {
      case Some(p) => p.scalaTypeName + "." + nameSymbol
      case None    => (`enum`.getFile.scalaPackagePartsAsSymbols :+ nameSymbol).mkString(".")
    }

    def scalaTypeNameWithMaybeRoot(context: Descriptor): String = {
      val fullName        = scalaTypeName
      val topLevelPackage = fullName.split('.')(0)
      if (context.fields.map(_.scalaName).contains(topLevelPackage))
        s"_root_.$fullName"
      else fullName
    }

    def isTopLevel = `enum`.getContainingType == null

    def javaTypeName = `enum`.getFile.fullJavaName(`enum`.getFullName)

    def javaConversions = `enum`.getFile.javaConversions

    def valuesWithNoDuplicates =
      `enum`.getValues.asScala
        .groupBy(_.getNumber)
        .mapValues(_.head)
        .values
        .toVector
        .sortBy(_.getNumber)

    def javaDescriptorSource: String =
      if (`enum`.isTopLevel)
        s"${`enum`.getFile.fileDescriptorObjectName}.javaDescriptor.getEnumTypes.get(${`enum`.getIndex})"
      else
        s"${`enum`.getContainingType.scalaTypeName}.javaDescriptor.getEnumTypes.get(${`enum`.getIndex})"

    def scalaDescriptorSource: String =
      if (`enum`.isTopLevel)
        s"${`enum`.getFile.fileDescriptorObjectName}.scalaDescriptor.enums(${`enum`.getIndex})"
      else s"${`enum`.getContainingType.scalaTypeName}.scalaDescriptor.enums(${`enum`.getIndex})"

    def baseTraitExtends: Seq[String] =
      "_root_.scalapb.GeneratedEnum" +: scalaOptions.getExtendsList.asScala.toSeq

    def companionExtends: Seq[String] =
      s"_root_.scalapb.GeneratedEnumCompanion[${nameSymbol}]" +: scalaOptions.getCompanionExtendsList.asScala.toSeq

    def sourcePath: Seq[Int] = {
      if (`enum`.isTopLevel) Seq(FileDescriptorProto.ENUM_TYPE_FIELD_NUMBER, `enum`.getIndex)
      else
        `enum`.getContainingType.sourcePath ++ Seq(
          DescriptorProto.ENUM_TYPE_FIELD_NUMBER,
          `enum`.getIndex
        )
    }

    def comment: Option[String] = {
      `enum`.getFile
        .findLocationByPath(sourcePath)
        .map(t => t.getLeadingComments + t.getTrailingComments)
        .map(Helper.escapeComment)
        .filter(_.nonEmpty)
    }
  }

  implicit class EnumValueDescriptorPimp(val enumValue: EnumValueDescriptor) {
    def scalaOptions: EnumValueOptions =
      enumValue.getOptions.getExtension[EnumValueOptions](Scalapb.enumValue)

    def valueExtends: Seq[String] =
      enumValue.getType.nameSymbol +: scalaOptions.getExtendsList.asScala.toSeq

    def isName = {
      Helper.makeUniqueNames(
        enumValue.getType.getValues.asScala
          .sortBy(v => (v.getNumber, v.getName))
          .map { e =>
            e -> ("is" + allCapsToCamelCase(e.getName, true))
          }
          .toSeq
      )(enumValue)
    }

    def sourcePath: Seq[Int] = {
      enumValue.getType.sourcePath ++ Seq(
        EnumDescriptorProto.VALUE_FIELD_NUMBER,
        enumValue.getIndex
      )
    }

    def comment: Option[String] = {
      enumValue.getFile
        .findLocationByPath(sourcePath)
        .map(t => t.getLeadingComments + t.getTrailingComments)
        .map(Helper.escapeComment)
        .filter(_.nonEmpty)
    }
  }

  implicit class FileDescriptorPimp(val file: FileDescriptor) {
    def scalaOptions: ScalaPbOptions =
      fileOptionsCache(file)

    def javaConversions = params.javaConversions && !scalaOptions.getTestOnlyNoJavaConversions

    def javaPackage: String = {
      if (file.getOptions.hasJavaPackage)
        file.getOptions.getJavaPackage
      else file.getPackage
    }

    def javaPackageAsSymbol: String =
      javaPackage.split('.').map(_.asSymbol).mkString(".")

    private def hasConflictingJavaClassName(className: String): Boolean =
      (file.getEnumTypes.asScala.exists(_.getName == className) ||
        file.getServices.asScala.exists(_.getName == className) ||
        file.getMessageTypes.asScala.exists(_.hasConflictingJavaClassName(className)))

    // This method does not scan recursively. Currently it is used to determine whether the file
    // companion object would conflict with any top-level generated class.
    private def hasConflictingScalaClassName(str: String): Boolean =
      file.getMessageTypes.asScala.exists(_.getName.toLowerCase == str.toLowerCase) ||
        file.getEnumTypes.asScala.exists(_.getName.toLowerCase == str.toLowerCase) ||
        file.getServices.asScala.exists(_.getName.toLowerCase == str.toLowerCase)

    def javaOuterClassName: String =
      if (file.getOptions.hasJavaOuterClassname)
        file.getOptions.getJavaOuterClassname
      else {
        val r = NameUtils.snakeCaseToCamelCase(baseName(file.getName), true)
        if (!hasConflictingJavaClassName(r)) r
        else r + "OuterClass"
      }

    private def isNonFlatDependency =
      (file.getPackage == "google.protobuf") || (file.getPackage == "scalapb")

    private def scalaPackageParts: Seq[String] = {
      val requestedPackageName: Seq[String] =
        (if (scalaOptions.hasPackageName) scalaOptions.getPackageName.split('.')
         else javaPackage.split('.')).toIndexedSeq.filterNot(_.isEmpty)

      if (scalaOptions.getFlatPackage || (params.flatPackage && !isNonFlatDependency))
        requestedPackageName
      else requestedPackageName ++ baseName(file.getName).replace('-', '_').split('.')
    }

    def scalaPackagePartsAsSymbols = {
      scalaPackageParts.map(_.asSymbol)
    }

    def scalaPackageName = {
      scalaPackagePartsAsSymbols.mkString(".")
    }

    def scalaDirectory = {
      scalaPackageParts.mkString("/")
    }

    def javaFullOuterClassName = {
      val pkg = javaPackageAsSymbol
      if (pkg.isEmpty) javaOuterClassName
      else pkg + "." + javaOuterClassName
    }

    private def stripPackageName(fullName: String): String =
      if (file.getPackage.isEmpty) fullName
      else {
        assert(fullName.startsWith(file.getPackage + "."))
        fullName.substring(file.getPackage.size + 1)
      }

    def fullJavaName(fullName: String) = {
      val base =
        if (!file.getOptions.getJavaMultipleFiles)
          (javaFullOuterClassName + ".")
        else {
          val pkg = javaPackageAsSymbol
          if (pkg.isEmpty) "" else (pkg + ".")
        }
      base + stripPackageName(fullName).split('.').map(_.asSymbol).mkString(".")
    }

    def fileDescriptorObjectName = {

      def inner(s: String): String =
        if (!hasConflictingJavaClassName(s) && !hasConflictingScalaClassName(s)) s
        else (s + "Companion")

      if (file.scalaOptions.hasObjectName) file.scalaOptions.getObjectName
      else
        inner(NameUtils.snakeCaseToCamelCase(baseName(file.getName) + "Proto", upperInitial = true))
    }

    def fileDescriptorObjectFullName: String =
      (scalaPackagePartsAsSymbols :+ fileDescriptorObjectName).mkString(".")

    def isProto2 = file.getSyntax == FileDescriptor.Syntax.PROTO2

    def isProto3 = file.getSyntax == FileDescriptor.Syntax.PROTO3

    def findLocationByPath(path: Seq[Int]): Option[SourceCodeInfo.Location] = {
      file.toProto.getSourceCodeInfo.getLocationList.asScala.find(_.getPathList.asScala == path)
    }

    def usePrimitiveWrappers: Boolean = !scalaOptions.getNoPrimitiveWrappers

    def retainSourceCodeInfo: Boolean = {
      if (scalaOptions.hasRetainSourceCodeInfo) scalaOptions.getRetainSourceCodeInfo
      else params.retainSourceCodeInfo
    }

    /** Returns a vector with all messages (both top-level and nested) in the file. */
    def allMessages: Vector[Descriptor] = {
      val messages = Vector.newBuilder[Descriptor]
      def visitMessage(d: Descriptor): Unit = {
        messages += d
        d.getNestedTypes.asScala.foreach(visitMessage)
      }
      file.getMessageTypes.asScala.foreach(visitMessage)
      messages.result()
    }
  }

  private def allCapsToCamelCase(name: String, upperInitial: Boolean = false): String = {
    val b = new StringBuilder()
    @annotation.tailrec
    def inner(name: String, capNext: Boolean): Unit = if (name.nonEmpty) {
      val (r, capNext2) = name.head match {
        case c if c.isUpper =>
          // capitalize according to capNext.
          (Some(if (capNext) c else c.toLower), false)
        case c if c.isLower =>
          // Lower caps never get capitalized, but will force
          // the next letter to be upper case.
          (Some(c), true)
        case c if c.isDigit => (Some(c), true)
        case _              => (None, true)
      }
      r.foreach(b.append)
      inner(name.tail, capNext2)
    }
    inner(name, upperInitial)
    b.toString
  }

  def baseName(fileName: String) =
    fileName.split("/").last.nn.replaceAll(raw"[.]proto$$|[.]protodevel", "")
}

private[scalapb] object DescriptorImplicits {
  val ScalaSeq      = "_root_.scala.Seq"
  val ScalaMap      = "_root_.scala.collection.immutable.Map"
  val ScalaVector   = "_root_.scala.collection.immutable.Vector"
  val ScalaIterable = "_root_.scala.collection.immutable.Iterable"
  val ScalaOption   = "_root_.scala.Option"
}

object Helper {
  def makeUniqueNames[T](values: Seq[(T, String)]): Map[T, String] = {
    val newNameMap: Map[String, T] =
      values.foldLeft(Map.empty[String, T]) {
        case (nameMap, (t, name)) =>
          var newName: String = name
          var attempt: Int    = 0
          while (nameMap.contains(newName)) {
            attempt += 1
            newName = s"${name}_$attempt"
          }
          nameMap + (newName -> t)
      }
    newNameMap.map(_.swap)
  }

  def escapeComment(s: String): String = {
    s.replace("&", "&amp;")
      .replace("/*", "/&#42;")
      .replace("*/", "*&#47;")
      .replace("@", "&#64;")
      .replace("<", "&lt;")
      .replace(">", "&gt;")
      .replace("\\", "&92;")
  }
}
