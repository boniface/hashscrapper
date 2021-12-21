package hashscrapper

import hashscrapper.utils.JSoup._
import org.jsoup.nodes.{Document, Element, TextNode}

import java.util.regex.Pattern

object DocumentCleaner {

  private val captionPattern = Pattern.compile("^caption$")
  private val googlePattern = Pattern.compile("google")
  private val facebookPattern = Pattern.compile("facebook")
  private val twitterPattern = Pattern.compile("twitter")
  /**
   * this regex is used to remove undesirable nodes from our doc
   * indicate that something maybe isn't content but more of a comment, footer or some other undesirable node
   */
  private val regExRemoveNodes = "^side$|combx|retweet|mediaarticlerelated|menucontainer|"+
  "navbar|storytopbar-bucket|utility-bar|inline-share-tools"+
  "|comment|PopularQuestions|contact|foot|footer|Footer|footnote"+
  "|cnn_strycaptiontxt|cnn_html_slideshow|cnn_strylftcntnt"+
  "|links|meta$|shoutbox|sponsor"+
  "|tags|socialnetworking|socialNetworking|cnnStryHghLght"+
  "|cnn_stryspcvbx|^inset$|pagetools|post-attributes"+
  "|welcome_form|contentTools2|the_answers"+
  "|communitypromo|runaroundLeft|subscribe|vcard|articleheadings"+
  "|date|^print$|popup|author-dropdown|tools|socialtools|byline"+
  "|konafilter|KonaFilter|breadcrumbs|^fn$|wp-caption-text"+
  "|legende|ajoutVideo|timestamp|js_replies"
  private val queryNaughtyIDs = "[id~=(" + regExRemoveNodes + ")]"
  private val queryNaughtyClasses = "[class~=(" + regExRemoveNodes + ")]"
  private val queryNaughtyNames = "[name~=(" + regExRemoveNodes + ")]"

  // FIX THIS FIRST
  def clean(doc: Document): Document = {

    //TODO right now this solution mutates this document
    // it would be very nice to implement this with an immutable solution
    implicit val docToClean: Document = doc.clone



//    cleanTextTags
//    removeScriptsAndStyles
//    cleanBadTags
//    removeNodesViaRegEx(captionPattern)
//    removeNodesViaRegEx(googlePattern)
//    removeNodesViaRegEx(facebookPattern)
//    removeNodesViaRegEx(twitterPattern)
//    cleanUpSpanTagsInParagraphs
    docToClean
  }

  /**
   * replaces various tags with textnodes
   */
  private def cleanTextTags(implicit doc: Document): Unit = {

    val res: Seq[Element] = byTag("em") ++ byTag("strong") ++ byTag("b") ++ byTag("i") ++
      byTag("strike") ++ byTag("del") ++ byTag("ins")

    res.foreach { node =>
      val tn = new TextNode(doc.baseUri) // node.text
      node.replaceWith(tn)
    }

  }


private def removeScriptsAndStyles (implicit doc: Document): Unit =
(byTag ("script") ++ byTag ("style") ++ byTag ("noscript") ).foreach (remove)

private def cleanBadTags (implicit doc: Document): Unit =
(select (queryNaughtyIDs) ++ select (queryNaughtyClasses) ++ select (queryNaughtyNames) ).foreach (remove)

/**
 * removes nodes that may have a certain pattern that matches against a class or id tag
 */
private def removeNodesViaRegEx (pattern: Pattern) (implicit doc: Document): Unit =
(byAttrRe ("id", pattern) ++ byAttrRe ("class", pattern) ).foreach (remove)

/**
 * takes care of the situation where you have a span tag nested in a paragraph tag
 * e.g. businessweek2.txt
 */
private def cleanUpSpanTagsInParagraphs (implicit doc: Document): Unit =
byTag ("span").filter (_.parent.nodeName == "p").foreach {

  node =>
  val tn = new TextNode (doc.baseUri) // node.TEXT
  node.replaceWith (tn)
}

}