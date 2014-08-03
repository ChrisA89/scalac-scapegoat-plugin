package com.sksamuel.scapegoat.inspections.collections

import com.sksamuel.scapegoat._

/** @author Stephen Samuel */
class FilterHeadOption extends Inspection {

  def inspector(context: InspectionContext): Inspector = new Inspector(context) {
    override def traverser = new context.Traverser {

      import context.global._

      override def traverse(tree: Tree): Unit = {
        tree match {
          case Select(Apply(Select(_, TermName("filter")), _), TermName("headOption")) =>
            context.warn("filter().headOption instead of find()", tree.pos, Levels.Info,
              ".filter(x => Bool).headOption can be replaced with find(x => Bool): " + tree.toString().take(500))
          case _ => super.traverse(tree)
        }
      }
    }
  }
}