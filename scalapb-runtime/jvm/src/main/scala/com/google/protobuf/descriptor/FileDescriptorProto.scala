// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package com.google.protobuf.descriptor

import scala.collection.JavaConverters._

/** Describes a complete .proto file.
  *
  * @param name
  *   file name, relative to root of source tree
  * @param package
  *   e.g. "foo", "foo.bar", etc.
  * @param dependency
  *   Names of files imported by this file.
  * @param publicDependency
  *   Indexes of the public imported files in the dependency list above.
  * @param weakDependency
  *   Indexes of the weak imported files in the dependency list.
  *   For Google-internal migration only. Do not use.
  * @param messageType
  *   All top-level definitions in this file.
  * @param sourceCodeInfo
  *   This field contains optional information about the original source code.
  *   You may safely remove this entire field without harming runtime
  *   functionality of the descriptors -- the information is needed only by
  *   development tools.
  * @param syntax
  *   The syntax of the proto file.
  *   The supported values are "proto2" and "proto3".
  */
@SerialVersionUID(0L)
final case class FileDescriptorProto(
    name: scala.Option[String] = None,
    `package`: scala.Option[String] = None,
    dependency: scala.collection.Seq[String] = Nil,
    publicDependency: scala.collection.Seq[Int] = Nil,
    weakDependency: scala.collection.Seq[Int] = Nil,
    messageType: scala.collection.Seq[com.google.protobuf.descriptor.DescriptorProto] = Nil,
    enumType: scala.collection.Seq[com.google.protobuf.descriptor.EnumDescriptorProto] = Nil,
    service: scala.collection.Seq[com.google.protobuf.descriptor.ServiceDescriptorProto] = Nil,
    extension: scala.collection.Seq[com.google.protobuf.descriptor.FieldDescriptorProto] = Nil,
    options: scala.Option[com.google.protobuf.descriptor.FileOptions] = None,
    sourceCodeInfo: scala.Option[com.google.protobuf.descriptor.SourceCodeInfo] = None,
    syntax: scala.Option[String] = None
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[FileDescriptorProto] with com.trueaccord.lenses.Updatable[FileDescriptorProto] {
    @transient
    private[this] var __serializedSizeCachedValue: Int = 0
    private[this] def __computeSerializedValue(): Int = {
      var __size = 0
      if (name.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, name.get) }
      if (`package`.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(2, `package`.get) }
      dependency.foreach(dependency => __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(3, dependency))
      publicDependency.foreach(publicDependency => __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(10, publicDependency))
      weakDependency.foreach(weakDependency => __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(11, weakDependency))
      messageType.foreach(messageType => __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(messageType.serializedSize) + messageType.serializedSize)
      enumType.foreach(enumType => __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(enumType.serializedSize) + enumType.serializedSize)
      service.foreach(service => __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(service.serializedSize) + service.serializedSize)
      extension.foreach(extension => __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(extension.serializedSize) + extension.serializedSize)
      if (options.isDefined) { __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(options.get.serializedSize) + options.get.serializedSize }
      if (sourceCodeInfo.isDefined) { __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(sourceCodeInfo.get.serializedSize) + sourceCodeInfo.get.serializedSize }
      if (syntax.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(12, syntax.get) }
      __size
    }
    final override def serializedSize: Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): Unit = {
      name.foreach { __v =>
        _output__.writeString(1, __v)
      };
      `package`.foreach { __v =>
        _output__.writeString(2, __v)
      };
      dependency.foreach { __v =>
        _output__.writeString(3, __v)
      };
      messageType.foreach { __v =>
        _output__.writeTag(4, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      enumType.foreach { __v =>
        _output__.writeTag(5, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      service.foreach { __v =>
        _output__.writeTag(6, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      extension.foreach { __v =>
        _output__.writeTag(7, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      options.foreach { __v =>
        _output__.writeTag(8, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      sourceCodeInfo.foreach { __v =>
        _output__.writeTag(9, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      publicDependency.foreach { __v =>
        _output__.writeInt32(10, __v)
      };
      weakDependency.foreach { __v =>
        _output__.writeInt32(11, __v)
      };
      syntax.foreach { __v =>
        _output__.writeString(12, __v)
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): com.google.protobuf.descriptor.FileDescriptorProto = {
      var __name = this.name
      var __package = this.`package`
      val __dependency = (scala.collection.immutable.Vector.newBuilder[String] ++= this.dependency)
      val __publicDependency = (scala.collection.immutable.Vector.newBuilder[Int] ++= this.publicDependency)
      val __weakDependency = (scala.collection.immutable.Vector.newBuilder[Int] ++= this.weakDependency)
      val __messageType = (scala.collection.immutable.Vector.newBuilder[com.google.protobuf.descriptor.DescriptorProto] ++= this.messageType)
      val __enumType = (scala.collection.immutable.Vector.newBuilder[com.google.protobuf.descriptor.EnumDescriptorProto] ++= this.enumType)
      val __service = (scala.collection.immutable.Vector.newBuilder[com.google.protobuf.descriptor.ServiceDescriptorProto] ++= this.service)
      val __extension = (scala.collection.immutable.Vector.newBuilder[com.google.protobuf.descriptor.FieldDescriptorProto] ++= this.extension)
      var __options = this.options
      var __sourceCodeInfo = this.sourceCodeInfo
      var __syntax = this.syntax
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __name = Some(_input__.readString())
          case 18 =>
            __package = Some(_input__.readString())
          case 26 =>
            __dependency += _input__.readString()
          case 80 =>
            __publicDependency += _input__.readInt32()
          case 82 => {
            val length = _input__.readRawVarint32()
            val oldLimit = _input__.pushLimit(length)
            while (_input__.getBytesUntilLimit > 0) {
              __publicDependency += _input__.readInt32
            }
            _input__.popLimit(oldLimit)
          }
          case 88 =>
            __weakDependency += _input__.readInt32()
          case 90 => {
            val length = _input__.readRawVarint32()
            val oldLimit = _input__.pushLimit(length)
            while (_input__.getBytesUntilLimit > 0) {
              __weakDependency += _input__.readInt32
            }
            _input__.popLimit(oldLimit)
          }
          case 34 =>
            __messageType += _root_.com.trueaccord.scalapb.LiteParser.readMessage(_input__, com.google.protobuf.descriptor.DescriptorProto.defaultInstance)
          case 42 =>
            __enumType += _root_.com.trueaccord.scalapb.LiteParser.readMessage(_input__, com.google.protobuf.descriptor.EnumDescriptorProto.defaultInstance)
          case 50 =>
            __service += _root_.com.trueaccord.scalapb.LiteParser.readMessage(_input__, com.google.protobuf.descriptor.ServiceDescriptorProto.defaultInstance)
          case 58 =>
            __extension += _root_.com.trueaccord.scalapb.LiteParser.readMessage(_input__, com.google.protobuf.descriptor.FieldDescriptorProto.defaultInstance)
          case 66 =>
            __options = Some(_root_.com.trueaccord.scalapb.LiteParser.readMessage(_input__, __options.getOrElse(com.google.protobuf.descriptor.FileOptions.defaultInstance)))
          case 74 =>
            __sourceCodeInfo = Some(_root_.com.trueaccord.scalapb.LiteParser.readMessage(_input__, __sourceCodeInfo.getOrElse(com.google.protobuf.descriptor.SourceCodeInfo.defaultInstance)))
          case 98 =>
            __syntax = Some(_input__.readString())
          case tag => _input__.skipField(tag)
        }
      }
      com.google.protobuf.descriptor.FileDescriptorProto(
          name = __name,
          `package` = __package,
          dependency = __dependency.result(),
          publicDependency = __publicDependency.result(),
          weakDependency = __weakDependency.result(),
          messageType = __messageType.result(),
          enumType = __enumType.result(),
          service = __service.result(),
          extension = __extension.result(),
          options = __options,
          sourceCodeInfo = __sourceCodeInfo,
          syntax = __syntax
      )
    }
    def getName: String = name.getOrElse("")
    def clearName: FileDescriptorProto = copy(name = None)
    def withName(__v: String): FileDescriptorProto = copy(name = Some(__v))
    def getPackage: String = `package`.getOrElse("")
    def clearPackage: FileDescriptorProto = copy(`package` = None)
    def withPackage(__v: String): FileDescriptorProto = copy(`package` = Some(__v))
    def clearDependency = copy(dependency = scala.collection.Seq.empty)
    def addDependency(__vs: String*): FileDescriptorProto = addAllDependency(__vs)
    def addAllDependency(__vs: TraversableOnce[String]): FileDescriptorProto = copy(dependency = dependency ++ __vs)
    def withDependency(__v: scala.collection.Seq[String]): FileDescriptorProto = copy(dependency = __v)
    def clearPublicDependency = copy(publicDependency = scala.collection.Seq.empty)
    def addPublicDependency(__vs: Int*): FileDescriptorProto = addAllPublicDependency(__vs)
    def addAllPublicDependency(__vs: TraversableOnce[Int]): FileDescriptorProto = copy(publicDependency = publicDependency ++ __vs)
    def withPublicDependency(__v: scala.collection.Seq[Int]): FileDescriptorProto = copy(publicDependency = __v)
    def clearWeakDependency = copy(weakDependency = scala.collection.Seq.empty)
    def addWeakDependency(__vs: Int*): FileDescriptorProto = addAllWeakDependency(__vs)
    def addAllWeakDependency(__vs: TraversableOnce[Int]): FileDescriptorProto = copy(weakDependency = weakDependency ++ __vs)
    def withWeakDependency(__v: scala.collection.Seq[Int]): FileDescriptorProto = copy(weakDependency = __v)
    def clearMessageType = copy(messageType = scala.collection.Seq.empty)
    def addMessageType(__vs: com.google.protobuf.descriptor.DescriptorProto*): FileDescriptorProto = addAllMessageType(__vs)
    def addAllMessageType(__vs: TraversableOnce[com.google.protobuf.descriptor.DescriptorProto]): FileDescriptorProto = copy(messageType = messageType ++ __vs)
    def withMessageType(__v: scala.collection.Seq[com.google.protobuf.descriptor.DescriptorProto]): FileDescriptorProto = copy(messageType = __v)
    def clearEnumType = copy(enumType = scala.collection.Seq.empty)
    def addEnumType(__vs: com.google.protobuf.descriptor.EnumDescriptorProto*): FileDescriptorProto = addAllEnumType(__vs)
    def addAllEnumType(__vs: TraversableOnce[com.google.protobuf.descriptor.EnumDescriptorProto]): FileDescriptorProto = copy(enumType = enumType ++ __vs)
    def withEnumType(__v: scala.collection.Seq[com.google.protobuf.descriptor.EnumDescriptorProto]): FileDescriptorProto = copy(enumType = __v)
    def clearService = copy(service = scala.collection.Seq.empty)
    def addService(__vs: com.google.protobuf.descriptor.ServiceDescriptorProto*): FileDescriptorProto = addAllService(__vs)
    def addAllService(__vs: TraversableOnce[com.google.protobuf.descriptor.ServiceDescriptorProto]): FileDescriptorProto = copy(service = service ++ __vs)
    def withService(__v: scala.collection.Seq[com.google.protobuf.descriptor.ServiceDescriptorProto]): FileDescriptorProto = copy(service = __v)
    def clearExtension = copy(extension = scala.collection.Seq.empty)
    def addExtension(__vs: com.google.protobuf.descriptor.FieldDescriptorProto*): FileDescriptorProto = addAllExtension(__vs)
    def addAllExtension(__vs: TraversableOnce[com.google.protobuf.descriptor.FieldDescriptorProto]): FileDescriptorProto = copy(extension = extension ++ __vs)
    def withExtension(__v: scala.collection.Seq[com.google.protobuf.descriptor.FieldDescriptorProto]): FileDescriptorProto = copy(extension = __v)
    def getOptions: com.google.protobuf.descriptor.FileOptions = options.getOrElse(com.google.protobuf.descriptor.FileOptions.defaultInstance)
    def clearOptions: FileDescriptorProto = copy(options = None)
    def withOptions(__v: com.google.protobuf.descriptor.FileOptions): FileDescriptorProto = copy(options = Some(__v))
    def getSourceCodeInfo: com.google.protobuf.descriptor.SourceCodeInfo = sourceCodeInfo.getOrElse(com.google.protobuf.descriptor.SourceCodeInfo.defaultInstance)
    def clearSourceCodeInfo: FileDescriptorProto = copy(sourceCodeInfo = None)
    def withSourceCodeInfo(__v: com.google.protobuf.descriptor.SourceCodeInfo): FileDescriptorProto = copy(sourceCodeInfo = Some(__v))
    def getSyntax: String = syntax.getOrElse("")
    def clearSyntax: FileDescriptorProto = copy(syntax = None)
    def withSyntax(__v: String): FileDescriptorProto = copy(syntax = Some(__v))
    def getField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): scala.Any = {
      __field.getNumber match {
        case 1 => name.orNull
        case 2 => `package`.orNull
        case 3 => dependency
        case 10 => publicDependency
        case 11 => weakDependency
        case 4 => messageType
        case 5 => enumType
        case 6 => service
        case 7 => extension
        case 8 => options.orNull
        case 9 => sourceCodeInfo.orNull
        case 12 => syntax.orNull
      }
    }
    override def toString: String = _root_.com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
    def companion = com.google.protobuf.descriptor.FileDescriptorProto
}

object FileDescriptorProto extends com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.descriptor.FileDescriptorProto] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.descriptor.FileDescriptorProto, com.google.protobuf.DescriptorProtos.FileDescriptorProto] {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.descriptor.FileDescriptorProto] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.descriptor.FileDescriptorProto, com.google.protobuf.DescriptorProtos.FileDescriptorProto] = this
  def toJavaProto(scalaPbSource: com.google.protobuf.descriptor.FileDescriptorProto): com.google.protobuf.DescriptorProtos.FileDescriptorProto = {
    val javaPbOut = com.google.protobuf.DescriptorProtos.FileDescriptorProto.newBuilder
    scalaPbSource.name.foreach(javaPbOut.setName)
    scalaPbSource.`package`.foreach(javaPbOut.setPackage)
    javaPbOut.addAllDependency(scalaPbSource.dependency.asJava)
    javaPbOut.addAllPublicDependency(scalaPbSource.publicDependency.map(Int.box(_)).asJava)
    javaPbOut.addAllWeakDependency(scalaPbSource.weakDependency.map(Int.box(_)).asJava)
    javaPbOut.addAllMessageType(scalaPbSource.messageType.map(com.google.protobuf.descriptor.DescriptorProto.toJavaProto(_)).asJava)
    javaPbOut.addAllEnumType(scalaPbSource.enumType.map(com.google.protobuf.descriptor.EnumDescriptorProto.toJavaProto(_)).asJava)
    javaPbOut.addAllService(scalaPbSource.service.map(com.google.protobuf.descriptor.ServiceDescriptorProto.toJavaProto(_)).asJava)
    javaPbOut.addAllExtension(scalaPbSource.extension.map(com.google.protobuf.descriptor.FieldDescriptorProto.toJavaProto(_)).asJava)
    scalaPbSource.options.map(com.google.protobuf.descriptor.FileOptions.toJavaProto(_)).foreach(javaPbOut.setOptions)
    scalaPbSource.sourceCodeInfo.map(com.google.protobuf.descriptor.SourceCodeInfo.toJavaProto(_)).foreach(javaPbOut.setSourceCodeInfo)
    scalaPbSource.syntax.foreach(javaPbOut.setSyntax)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: com.google.protobuf.DescriptorProtos.FileDescriptorProto): com.google.protobuf.descriptor.FileDescriptorProto = com.google.protobuf.descriptor.FileDescriptorProto(
    name = if (javaPbSource.hasName) Some(javaPbSource.getName) else None,
    `package` = if (javaPbSource.hasPackage) Some(javaPbSource.getPackage) else None,
    dependency = javaPbSource.getDependencyList.asScala,
    publicDependency = javaPbSource.getPublicDependencyList.asScala.map(_.intValue),
    weakDependency = javaPbSource.getWeakDependencyList.asScala.map(_.intValue),
    messageType = javaPbSource.getMessageTypeList.asScala.map(com.google.protobuf.descriptor.DescriptorProto.fromJavaProto(_)),
    enumType = javaPbSource.getEnumTypeList.asScala.map(com.google.protobuf.descriptor.EnumDescriptorProto.fromJavaProto(_)),
    service = javaPbSource.getServiceList.asScala.map(com.google.protobuf.descriptor.ServiceDescriptorProto.fromJavaProto(_)),
    extension = javaPbSource.getExtensionList.asScala.map(com.google.protobuf.descriptor.FieldDescriptorProto.fromJavaProto(_)),
    options = if (javaPbSource.hasOptions) Some(com.google.protobuf.descriptor.FileOptions.fromJavaProto(javaPbSource.getOptions)) else None,
    sourceCodeInfo = if (javaPbSource.hasSourceCodeInfo) Some(com.google.protobuf.descriptor.SourceCodeInfo.fromJavaProto(javaPbSource.getSourceCodeInfo)) else None,
    syntax = if (javaPbSource.hasSyntax) Some(javaPbSource.getSyntax) else None
  )
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): com.google.protobuf.descriptor.FileDescriptorProto = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    com.google.protobuf.descriptor.FileDescriptorProto(
      __fieldsMap.get(__fields.get(0)).asInstanceOf[scala.Option[String]],
      __fieldsMap.get(__fields.get(1)).asInstanceOf[scala.Option[String]],
      __fieldsMap.getOrElse(__fields.get(2), Nil).asInstanceOf[scala.collection.Seq[String]],
      __fieldsMap.getOrElse(__fields.get(3), Nil).asInstanceOf[scala.collection.Seq[Int]],
      __fieldsMap.getOrElse(__fields.get(4), Nil).asInstanceOf[scala.collection.Seq[Int]],
      __fieldsMap.getOrElse(__fields.get(5), Nil).asInstanceOf[scala.collection.Seq[com.google.protobuf.descriptor.DescriptorProto]],
      __fieldsMap.getOrElse(__fields.get(6), Nil).asInstanceOf[scala.collection.Seq[com.google.protobuf.descriptor.EnumDescriptorProto]],
      __fieldsMap.getOrElse(__fields.get(7), Nil).asInstanceOf[scala.collection.Seq[com.google.protobuf.descriptor.ServiceDescriptorProto]],
      __fieldsMap.getOrElse(__fields.get(8), Nil).asInstanceOf[scala.collection.Seq[com.google.protobuf.descriptor.FieldDescriptorProto]],
      __fieldsMap.get(__fields.get(9)).asInstanceOf[scala.Option[com.google.protobuf.descriptor.FileOptions]],
      __fieldsMap.get(__fields.get(10)).asInstanceOf[scala.Option[com.google.protobuf.descriptor.SourceCodeInfo]],
      __fieldsMap.get(__fields.get(11)).asInstanceOf[scala.Option[String]]
    )
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = DescriptorProtoCompanion.javaDescriptor.getMessageTypes.get(1)
  def messageCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = {
    require(__field.getContainingType() == javaDescriptor, "FieldDescriptor does not match message type.")
    var __out: _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = null
    __field.getNumber match {
      case 4 => __out = com.google.protobuf.descriptor.DescriptorProto
      case 5 => __out = com.google.protobuf.descriptor.EnumDescriptorProto
      case 6 => __out = com.google.protobuf.descriptor.ServiceDescriptorProto
      case 7 => __out = com.google.protobuf.descriptor.FieldDescriptorProto
      case 8 => __out = com.google.protobuf.descriptor.FileOptions
      case 9 => __out = com.google.protobuf.descriptor.SourceCodeInfo
    }
  __out
  }
  def enumCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__field)
  lazy val defaultInstance = com.google.protobuf.descriptor.FileDescriptorProto(
  )
  implicit class FileDescriptorProtoLens[UpperPB](_l: _root_.com.trueaccord.lenses.Lens[UpperPB, com.google.protobuf.descriptor.FileDescriptorProto]) extends _root_.com.trueaccord.lenses.ObjectLens[UpperPB, com.google.protobuf.descriptor.FileDescriptorProto](_l) {
    def name: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.getName)((c_, f_) => c_.copy(name = Some(f_)))
    def optionalName: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[String]] = field(_.name)((c_, f_) => c_.copy(name = f_))
    def `package`: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.getPackage)((c_, f_) => c_.copy(`package` = Some(f_)))
    def optionalPackage: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[String]] = field(_.`package`)((c_, f_) => c_.copy(`package` = f_))
    def dependency: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[String]] = field(_.dependency)((c_, f_) => c_.copy(dependency = f_))
    def publicDependency: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[Int]] = field(_.publicDependency)((c_, f_) => c_.copy(publicDependency = f_))
    def weakDependency: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[Int]] = field(_.weakDependency)((c_, f_) => c_.copy(weakDependency = f_))
    def messageType: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[com.google.protobuf.descriptor.DescriptorProto]] = field(_.messageType)((c_, f_) => c_.copy(messageType = f_))
    def enumType: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[com.google.protobuf.descriptor.EnumDescriptorProto]] = field(_.enumType)((c_, f_) => c_.copy(enumType = f_))
    def service: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[com.google.protobuf.descriptor.ServiceDescriptorProto]] = field(_.service)((c_, f_) => c_.copy(service = f_))
    def extension: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[com.google.protobuf.descriptor.FieldDescriptorProto]] = field(_.extension)((c_, f_) => c_.copy(extension = f_))
    def options: _root_.com.trueaccord.lenses.Lens[UpperPB, com.google.protobuf.descriptor.FileOptions] = field(_.getOptions)((c_, f_) => c_.copy(options = Some(f_)))
    def optionalOptions: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[com.google.protobuf.descriptor.FileOptions]] = field(_.options)((c_, f_) => c_.copy(options = f_))
    def sourceCodeInfo: _root_.com.trueaccord.lenses.Lens[UpperPB, com.google.protobuf.descriptor.SourceCodeInfo] = field(_.getSourceCodeInfo)((c_, f_) => c_.copy(sourceCodeInfo = Some(f_)))
    def optionalSourceCodeInfo: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[com.google.protobuf.descriptor.SourceCodeInfo]] = field(_.sourceCodeInfo)((c_, f_) => c_.copy(sourceCodeInfo = f_))
    def syntax: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.getSyntax)((c_, f_) => c_.copy(syntax = Some(f_)))
    def optionalSyntax: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[String]] = field(_.syntax)((c_, f_) => c_.copy(syntax = f_))
  }
  final val NAME_FIELD_NUMBER = 1
  final val PACKAGE_FIELD_NUMBER = 2
  final val DEPENDENCY_FIELD_NUMBER = 3
  final val PUBLIC_DEPENDENCY_FIELD_NUMBER = 10
  final val WEAK_DEPENDENCY_FIELD_NUMBER = 11
  final val MESSAGE_TYPE_FIELD_NUMBER = 4
  final val ENUM_TYPE_FIELD_NUMBER = 5
  final val SERVICE_FIELD_NUMBER = 6
  final val EXTENSION_FIELD_NUMBER = 7
  final val OPTIONS_FIELD_NUMBER = 8
  final val SOURCE_CODE_INFO_FIELD_NUMBER = 9
  final val SYNTAX_FIELD_NUMBER = 12
}
