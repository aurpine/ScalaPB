// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package com.google.protobuf.empty

import scala.collection.JavaConverters._

/** A generic empty message that you can re-use to avoid defining duplicated
  * empty messages in your APIs. A typical example is to use it as the request
  * or the response type of an API method. For instance:
  *
  *     service Foo {
  *       rpc Bar(google.protobuf.Empty) returns (google.protobuf.Empty);
  *     }
  *
  * The JSON representation for `Empty` is empty JSON object `{}`.
  */
@SerialVersionUID(0L)
final case class Empty(
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[Empty] with com.trueaccord.lenses.Updatable[Empty] {
    final override def serializedSize: Int = 0
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): Unit = {
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): com.google.protobuf.empty.Empty = {
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case tag => _input__.skipField(tag)
        }
      }
      com.google.protobuf.empty.Empty(
      )
    }
    def getField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): scala.Any = throw new MatchError(__field)
    override def toString: String = _root_.com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
    def companion = com.google.protobuf.empty.Empty
}

object Empty extends com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.empty.Empty] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.empty.Empty, com.google.protobuf.Empty] {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[com.google.protobuf.empty.Empty] with com.trueaccord.scalapb.JavaProtoSupport[com.google.protobuf.empty.Empty, com.google.protobuf.Empty] = this
  def toJavaProto(scalaPbSource: com.google.protobuf.empty.Empty): com.google.protobuf.Empty = {
    val javaPbOut = com.google.protobuf.Empty.newBuilder
    javaPbOut.build
  }
  def fromJavaProto(javaPbSource: com.google.protobuf.Empty): com.google.protobuf.empty.Empty = com.google.protobuf.empty.Empty(
  )
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): com.google.protobuf.empty.Empty = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    com.google.protobuf.empty.Empty(
    )
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = EmptyProto.javaDescriptor.getMessageTypes.get(0)
  def messageCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__field)
  def enumCompanionForField(__field: _root_.com.google.protobuf.Descriptors.FieldDescriptor): _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__field)
  lazy val defaultInstance = com.google.protobuf.empty.Empty(
  )
  implicit class EmptyLens[UpperPB](_l: _root_.com.trueaccord.lenses.Lens[UpperPB, com.google.protobuf.empty.Empty]) extends _root_.com.trueaccord.lenses.ObjectLens[UpperPB, com.google.protobuf.empty.Empty](_l) {
  }
}
