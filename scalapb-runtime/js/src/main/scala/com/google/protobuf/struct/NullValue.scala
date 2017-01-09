// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package com.google.protobuf.struct



sealed trait NullValue extends _root_.com.trueaccord.scalapb.GeneratedEnum {
  type EnumType = NullValue
  def isNullValue: Boolean = false
  def isUnrecognized: Boolean = false
  def companion: _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[NullValue] = NullValue
}

object NullValue extends _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[NullValue] {
  implicit def enumCompanion: _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[NullValue] = this
  @SerialVersionUID(0L)
  case object NULL_VALUE extends NullValue {
    val value = 0
    val index = 0
    val name = "NULL_VALUE"
    override def isNullValue: Boolean = true
  }
  
  @SerialVersionUID(0L)
  case class Unrecognized(value: Int) extends NullValue {
    val name = "UNRECOGNIZED"
    val index = -1
    override def isUnrecognized: Boolean = true
  }
  
  lazy val values = scala.collection.Seq(NULL_VALUE)
  def fromValue(value: Int): NullValue = value match {
    case 0 => NULL_VALUE
    case __other => Unrecognized(__other)
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.EnumDescriptor = StructProto.javaDescriptor.getEnumTypes.get(0)
}