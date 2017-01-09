// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO2

package com.google.protobuf.descriptor

import scala.collection.JavaConverters._

/** A message representing a option the parser does not recognize. This only
  * appears in options protos created by the compiler::Parser class.
  * DescriptorPool resolves these when building Descriptor objects. Therefore,
  * options protos in descriptor objects (e.g. returned by Descriptor::options(),
  * or produced by Descriptor::CopyTo()) will never have UninterpretedOptions
  * in them.
  *
  * @param identifierValue
  *   The value of the uninterpreted option, in whatever type the tokenizer
  *   identified it as during parsing. Exactly one of these should be set.
  */
@SerialVersionUID(0L)
final case class UninterpretedOption(
    name: scala.collection.Seq[com.google.protobuf.descriptor.UninterpretedOption.NamePart] = Nil,
    identifierValue: scala.Option[String] = None,
    positiveIntValue: scala.Option[Long] = None,
    negativeIntValue: scala.Option[Long] = None,
    doubleValue: scala.Option[Double] = None,
    stringValue: scala.Option[_root_.com.google.protobuf.ByteString] = None,
    aggregateValue: scala.Option[String] = None
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[UninterpretedOption] with com.trueaccord.lenses.Updatable[UninterpretedOption] {
    @transient
    private[this] var __serializedSizeCachedValue: Int = 0
    private[this] def __computeSerializedValue(): Int = {
      var __size = 0
      name.foreach(name => __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(name.serializedSize) + name.serializedSize)
      if (identifierValue.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(3, identifierValue.get) }
      if (positiveIntValue.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeUInt64Size(4, positiveIntValue.get) }
      if (negativeIntValue.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(5, negativeIntValue.get) }
      if (doubleValue.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeDoubleSize(6, doubleValue.get) }
      if (stringValue.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeBytesSize(7, stringValue.get) }
      if (aggregateValue.isDefined) { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(8, aggregateValue.get) }
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
        _output__.writeTag(2, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      identifierValue.foreach { __v =>
        _output__.writeString(3, __v)
      };
      positiveIntValue.foreach { __v =>
        _output__.writeUInt64(4, __v)
      };
      negativeIntValue.foreach { __v =>
        _output__.writeInt64(5, __v)
      };
      doubleValue.foreach { __v =>
        _output__.writeDouble(6, __v)
      };
      stringValue.foreach { __v =>
        _output__.writeBytes(7, __v)
      };
      aggregateValue.foreach { __v =>
        _output__.writeString(8, __v)
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): com.google.protobuf.descriptor.UninterpretedOption = {
      val __name = (scala.collection.immutable.Vector.newBuilder[com.google.protobuf.descriptor.UninterpretedOption.NamePart] ++= this.name)
      var __identifierValue = this.identifierValue
      var __positiveIntValue = this.positiveIntValue
      var __negativeIntValue = this.negativeIntValue
      var __doubleValue = this.doubleValue
      var __stringValue = this.stringValue
      var __aggregateValue = this.aggregateValue
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 18 =>
            __name += _root_.com.trueaccord.scalapb.LiteParser.readMessage(_input__, com.google.protobuf.descriptor.UninterpretedOption.NamePart.defaultInstance)
          case 26 =>
            __identifierValue = Some(_input__.readString())
          case 32 =>
            __positiveIntValue = Some(_input__.readUInt64())
          case 40 =>
            __negativeIntValue = Some(_input__.readInt64())
          case 49 =>
            __doubleValue = Some(_input__.readDouble())
          case 58 =>
            __stringValue = Some(_input__.readBytes())
          case 66 =>
            __aggregateValue = Some(_input__.readString())
          case tag => _input__.skipField(tag)
        }
      }
      com.google.protobuf.descriptor.UninterpretedOption(
          name = __name.result(),
          identifierValue = __identifierValue,
          positiveIntValue = __positiveIntValue,
          negativeIntValue = __negativeIntValue,
          doubleValue = __doubleValue,
          stringValue = __stringValue,
          aggregateValue = __aggregateValue
      )
    }
    def clearName = copy(name = scala.collection.Seq.empty)
    def addName(__vs: com.google.protobuf.descriptor.UninterpretedOption.NamePart*): UninterpretedOption = addAllName(__vs)
    def addAllName(__vs: TraversableOnce[com.google.protobuf.descriptor.UninterpretedOption.NamePart]): UninterpretedOption = copy(name = name ++ __vs)
    def withName(__v: scala.collection.Seq[com.google.protobuf.descriptor.UninterpretedOption.NamePart]): UninterpretedOption = copy(name = __v)
    def getIdentifierValue: String = identifierValue.getOrElse("")
    def clearIdentifierValue: UninterpretedOption = copy(identifierValue = None)
    def withIdentifierValue(__v: String): UninterpretedOption = copy(identifierValue = Some(__v))
    def getPositiveIntValue: Long = positiveIntValue.getOrElse(0L)
    def clearPositiveIntValue: UninterpretedOption = copy(positiveIntValue = None)
    def withPositiveIntValue(__v: Long): UninterpretedOption = copy(positiveIntValue = Some(__v))
    def getNegativeIntValue: Long = negativeIntValue.getOrElse(0L)
    def clearNegativeIntValue: UninterpretedOption = copy(negativeIntValue = None)
    def withNegativeIntValue(__v: Long): UninterpretedOption = copy(negativeIntValue = Some(__v))
    def getDoubleValue: Double = doubleValue.getOrElse(0.0)
    def clearDoubleValue: UninterpretedOption = copy(doubleValue = None)
    def withDoubleValue(__v: Double): UninterpretedOption = copy(doubleValue = Some(__v))
    def getStringValue: _root_.com.google.protobuf.ByteString = stringValue.getOrElse(_root_.com.google.protobuf.ByteString.EMPTY)
    def clearStringValue: UninterpretedOption = copy(stringValue = None)
    def withStringValue(__v: _root_.com.google.protobuf.ByteString): UninterpretedOption = copy(stringValue = Some(__v))
    def getAggregateValue: String = aggregateValue.getOrElse("")
    def clearAggregateValue: UninterpretedOption = copy(aggregateValue = None)
    def withAggregateValue(__v: String): UninterpretedOption = copy(aggregateValue = Some(__v))
    def getField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): scala.Any = {
      __field.getNumber match {
        case 2 => name
        case 3 => identifierValue.orNull
        case 4 => positiveIntValue.orNull
        case 5 => negativeIntValue.orNull
        case 6 => doubleValue.orNull
        case 7 => stringValue.orNull
        case 8 => aggregateValue.orNull
      }
    }
    override def toString: String = _root_.com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
    def companion = com.google.protobuf.descriptor.UninterpretedOption
}

object UninterpretedOption extends com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.descriptor.UninterpretedOption] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.descriptor.UninterpretedOption, com.google.protobuf.DescriptorProtos.UninterpretedOption] {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.descriptor.UninterpretedOption] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.descriptor.UninterpretedOption, com.google.protobuf.DescriptorProtos.UninterpretedOption] = this
  def toJavaProto(scalaPbSource: com.google.protobuf.descriptor.UninterpretedOption): com.google.protobuf.DescriptorProtos.UninterpretedOption = {
    val javaPbOut = com.google.protobuf.DescriptorProtos.UninterpretedOption.newBuilder
    javaPbOut.addAllName(scalaPbSource.name.map(com.google.protobuf.descriptor.UninterpretedOption.NamePart.toJavaProto(_)).asJava)
    scalaPbSource.identifierValue.foreach(javaPbOut.setIdentifierValue)
    scalaPbSource.positiveIntValue.foreach(javaPbOut.setPositiveIntValue)
    scalaPbSource.negativeIntValue.foreach(javaPbOut.setNegativeIntValue)
    scalaPbSource.doubleValue.foreach(javaPbOut.setDoubleValue)
    scalaPbSource.stringValue.foreach(javaPbOut.setStringValue)
    scalaPbSource.aggregateValue.foreach(javaPbOut.setAggregateValue)
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: com.google.protobuf.DescriptorProtos.UninterpretedOption): com.google.protobuf.descriptor.UninterpretedOption = com.google.protobuf.descriptor.UninterpretedOption(
    name = javaPbSource.getNameList.asScala.map(com.google.protobuf.descriptor.UninterpretedOption.NamePart.fromJavaProto(_)),
    identifierValue = if (javaPbSource.hasIdentifierValue) Some(javaPbSource.getIdentifierValue) else None,
    positiveIntValue = if (javaPbSource.hasPositiveIntValue) Some(javaPbSource.getPositiveIntValue.longValue) else None,
    negativeIntValue = if (javaPbSource.hasNegativeIntValue) Some(javaPbSource.getNegativeIntValue.longValue) else None,
    doubleValue = if (javaPbSource.hasDoubleValue) Some(javaPbSource.getDoubleValue.doubleValue) else None,
    stringValue = if (javaPbSource.hasStringValue) Some(javaPbSource.getStringValue) else None,
    aggregateValue = if (javaPbSource.hasAggregateValue) Some(javaPbSource.getAggregateValue) else None
  )
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): com.google.protobuf.descriptor.UninterpretedOption = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    com.google.protobuf.descriptor.UninterpretedOption(
      __fieldsMap.getOrElse(__fields.get(0), Nil).asInstanceOf[scala.collection.Seq[com.google.protobuf.descriptor.UninterpretedOption.NamePart]],
      __fieldsMap.get(__fields.get(1)).asInstanceOf[scala.Option[String]],
      __fieldsMap.get(__fields.get(2)).asInstanceOf[scala.Option[Long]],
      __fieldsMap.get(__fields.get(3)).asInstanceOf[scala.Option[Long]],
      __fieldsMap.get(__fields.get(4)).asInstanceOf[scala.Option[Double]],
      __fieldsMap.get(__fields.get(5)).asInstanceOf[scala.Option[_root_.com.google.protobuf.ByteString]],
      __fieldsMap.get(__fields.get(6)).asInstanceOf[scala.Option[String]]
    )
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = DescriptorProtoCompanion.javaDescriptor.getMessageTypes.get(17)
  def messageCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = {
    require(__field.getContainingType() == javaDescriptor, "FieldDescriptor does not match message type.")
    var __out: _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = null
    __field.getNumber match {
      case 2 => __out = com.google.protobuf.descriptor.UninterpretedOption.NamePart
    }
  __out
  }
  def enumCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__field)
  lazy val defaultInstance = com.google.protobuf.descriptor.UninterpretedOption(
  )
  /** The name of the uninterpreted option.  Each string represents a segment in
    * a dot-separated name.  is_extension is true iff a segment represents an
    * extension (denoted with parentheses in options specs in .proto files).
    * E.g.,{ ["foo", false], ["bar.baz", true], ["qux", false] } represents
    * "foo.(bar.baz).qux".
    */
  @SerialVersionUID(0L)
  final case class NamePart(
      namePart: String,
      isExtension: Boolean
      ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[NamePart] with com.trueaccord.lenses.Updatable[NamePart] {
      @transient
      private[this] var __serializedSizeCachedValue: Int = 0
      private[this] def __computeSerializedValue(): Int = {
        var __size = 0
        __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, namePart)
        __size += _root_.com.google.protobuf.CodedOutputStream.computeBoolSize(2, isExtension)
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
        _output__.writeString(1, namePart)
        _output__.writeBool(2, isExtension)
      }
      def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): com.google.protobuf.descriptor.UninterpretedOption.NamePart = {
        var __namePart = this.namePart
        var __isExtension = this.isExtension
        var __requiredFields0: Long = 0x3L
        var _done__ = false
        while (!_done__) {
          val _tag__ = _input__.readTag()
          _tag__ match {
            case 0 => _done__ = true
            case 10 =>
              __namePart = _input__.readString()
              __requiredFields0 &= 0xfffffffffffffffeL
            case 16 =>
              __isExtension = _input__.readBool()
              __requiredFields0 &= 0xfffffffffffffffdL
            case tag => _input__.skipField(tag)
          }
        }
        if (__requiredFields0 != 0L) { throw new _root_.com.google.protobuf.InvalidProtocolBufferException("Message missing required fields.") } 
        com.google.protobuf.descriptor.UninterpretedOption.NamePart(
            namePart = __namePart,
            isExtension = __isExtension
        )
      }
      def withNamePart(__v: String): NamePart = copy(namePart = __v)
      def withIsExtension(__v: Boolean): NamePart = copy(isExtension = __v)
      def getField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): scala.Any = {
        __field.getNumber match {
          case 1 => namePart
          case 2 => isExtension
        }
      }
      override def toString: String = _root_.com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
      def companion = com.google.protobuf.descriptor.UninterpretedOption.NamePart
  }
  
  object NamePart extends com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.descriptor.UninterpretedOption.NamePart] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.descriptor.UninterpretedOption.NamePart, com.google.protobuf.DescriptorProtos.UninterpretedOption.NamePart] {
    implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.descriptor.UninterpretedOption.NamePart] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.descriptor.UninterpretedOption.NamePart, com.google.protobuf.DescriptorProtos.UninterpretedOption.NamePart] = this
    def toJavaProto(scalaPbSource: com.google.protobuf.descriptor.UninterpretedOption.NamePart): com.google.protobuf.DescriptorProtos.UninterpretedOption.NamePart = {
      val javaPbOut = com.google.protobuf.DescriptorProtos.UninterpretedOption.NamePart.newBuilder
      javaPbOut.setNamePart(scalaPbSource.namePart)
      javaPbOut.setIsExtension(scalaPbSource.isExtension)
      javaPbOut.build
    }
    def fromJavaProto(javaPbSource: com.google.protobuf.DescriptorProtos.UninterpretedOption.NamePart): com.google.protobuf.descriptor.UninterpretedOption.NamePart = com.google.protobuf.descriptor.UninterpretedOption.NamePart(
      namePart = javaPbSource.getNamePart,
      isExtension = javaPbSource.getIsExtension.booleanValue
    )
    def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): com.google.protobuf.descriptor.UninterpretedOption.NamePart = {
      require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
      val __fields = javaDescriptor.getFields
      com.google.protobuf.descriptor.UninterpretedOption.NamePart(
        __fieldsMap(__fields.get(0)).asInstanceOf[String],
        __fieldsMap(__fields.get(1)).asInstanceOf[Boolean]
      )
    }
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = com.google.protobuf.descriptor.UninterpretedOption.javaDescriptor.getNestedTypes.get(0)
    def messageCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__field)
    def enumCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__field)
    lazy val defaultInstance = com.google.protobuf.descriptor.UninterpretedOption.NamePart(
      namePart = "",
      isExtension = false
    )
    implicit class NamePartLens[UpperPB](_l: _root_.com.trueaccord.lenses.Lens[UpperPB, com.google.protobuf.descriptor.UninterpretedOption.NamePart]) extends _root_.com.trueaccord.lenses.ObjectLens[UpperPB, com.google.protobuf.descriptor.UninterpretedOption.NamePart](_l) {
      def namePart: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.namePart)((c_, f_) => c_.copy(namePart = f_))
      def isExtension: _root_.com.trueaccord.lenses.Lens[UpperPB, Boolean] = field(_.isExtension)((c_, f_) => c_.copy(isExtension = f_))
    }
    final val NAME_PART_FIELD_NUMBER = 1
    final val IS_EXTENSION_FIELD_NUMBER = 2
  }
  
  implicit class UninterpretedOptionLens[UpperPB](_l: _root_.com.trueaccord.lenses.Lens[UpperPB, com.google.protobuf.descriptor.UninterpretedOption]) extends _root_.com.trueaccord.lenses.ObjectLens[UpperPB, com.google.protobuf.descriptor.UninterpretedOption](_l) {
    def name: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.collection.Seq[com.google.protobuf.descriptor.UninterpretedOption.NamePart]] = field(_.name)((c_, f_) => c_.copy(name = f_))
    def identifierValue: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.getIdentifierValue)((c_, f_) => c_.copy(identifierValue = Some(f_)))
    def optionalIdentifierValue: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[String]] = field(_.identifierValue)((c_, f_) => c_.copy(identifierValue = f_))
    def positiveIntValue: _root_.com.trueaccord.lenses.Lens[UpperPB, Long] = field(_.getPositiveIntValue)((c_, f_) => c_.copy(positiveIntValue = Some(f_)))
    def optionalPositiveIntValue: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[Long]] = field(_.positiveIntValue)((c_, f_) => c_.copy(positiveIntValue = f_))
    def negativeIntValue: _root_.com.trueaccord.lenses.Lens[UpperPB, Long] = field(_.getNegativeIntValue)((c_, f_) => c_.copy(negativeIntValue = Some(f_)))
    def optionalNegativeIntValue: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[Long]] = field(_.negativeIntValue)((c_, f_) => c_.copy(negativeIntValue = f_))
    def doubleValue: _root_.com.trueaccord.lenses.Lens[UpperPB, Double] = field(_.getDoubleValue)((c_, f_) => c_.copy(doubleValue = Some(f_)))
    def optionalDoubleValue: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[Double]] = field(_.doubleValue)((c_, f_) => c_.copy(doubleValue = f_))
    def stringValue: _root_.com.trueaccord.lenses.Lens[UpperPB, _root_.com.google.protobuf.ByteString] = field(_.getStringValue)((c_, f_) => c_.copy(stringValue = Some(f_)))
    def optionalStringValue: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[_root_.com.google.protobuf.ByteString]] = field(_.stringValue)((c_, f_) => c_.copy(stringValue = f_))
    def aggregateValue: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.getAggregateValue)((c_, f_) => c_.copy(aggregateValue = Some(f_)))
    def optionalAggregateValue: _root_.com.trueaccord.lenses.Lens[UpperPB, scala.Option[String]] = field(_.aggregateValue)((c_, f_) => c_.copy(aggregateValue = f_))
  }
  final val NAME_FIELD_NUMBER = 2
  final val IDENTIFIER_VALUE_FIELD_NUMBER = 3
  final val POSITIVE_INT_VALUE_FIELD_NUMBER = 4
  final val NEGATIVE_INT_VALUE_FIELD_NUMBER = 5
  final val DOUBLE_VALUE_FIELD_NUMBER = 6
  final val STRING_VALUE_FIELD_NUMBER = 7
  final val AGGREGATE_VALUE_FIELD_NUMBER = 8
}
