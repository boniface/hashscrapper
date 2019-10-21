package hashscrapper.utils

import java.util.regex.Pattern

import org.jsoup.nodes.{Element, Document}

import scala.jdk.CollectionConverters._

object JSoup {
  def byTag(tag: String)(implicit elem: Element): Seq[Element] =
    elem.getElementsByTag(tag).asScala.toSeq

  def byAttrRe(attr: String, pattern: Pattern)(implicit doc: Document): Seq[Element] =
    doc.getElementsByAttributeValueMatching(attr, pattern).asScala.toSeq

  def byAttr(value: String)(implicit elem: Element): Seq[Element] =
    elem.getElementsByAttribute(value).asScala.toSeq

  def select(query: String)(implicit elem: Element): Seq[Element] =
    elem.select(query).asScala.toSeq

  def remove(elem: Element): Unit =
    Option(elem.parent()).foreach(_ => elem.remove())

}
