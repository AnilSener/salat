package com.github.salat.transformers

import com.github.salat.util.Logging

abstract class CustomTransformer[ModelObject <: AnyRef: Manifest, SerializedRepr <: AnyRef: Manifest]() extends Logging {

  final def in(value: Any): Any = {
    //    log.debug("%s\nin: value: %s", toString, value)
    value match {
      case Some(o: SerializedRepr) => deserialize(o)
      case o: SerializedRepr       => deserialize(o)
      case _                       => None
    }

  }
  final def out(value: Any): Option[SerializedRepr] = value match {
    case i: ModelObject => Option(serialize(i))
  }

  def path = manifest[ModelObject].runtimeClass.getName

  def deserialize(b: SerializedRepr): ModelObject

  def serialize(a: ModelObject): SerializedRepr

  val supportsGrater = manifest[SerializedRepr].runtimeClass.getName.endsWith("DBObject")

  override def toString = "CustomTransformer[ %s <-> %s ]".format(manifest[ModelObject].runtimeClass.getName, manifest[SerializedRepr].runtimeClass.getName)
}
