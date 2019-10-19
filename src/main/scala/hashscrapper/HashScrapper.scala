package hashscrapper

import hashscrapper.domain.Article
import hashscrapper.extractors.ContentExtractor._
import hashscrapper.opengraph.OpenGraphData
import org.jsoup.Jsoup

import scala.util.Try


object HashScrapper {

  def extract(html: String, lang: String = "all"): Option[Article] = {
    //This is replacing the non-breaking space with a regular space
    val sanitised = html.replace(' ', ' ')
    Try(Jsoup.parse(sanitised)).toOption.map { doc =>
      val canonicalLink = extractCanonicalLink(doc)
      val publishDate = extractDate(doc)
        .map(_.toDate)
        .orElse(canonicalLink.flatMap(extractDateFromURL))
      val rawTitle = extractTitle(doc)
      val info = Article(title = rawTitle,
        processedTitle = processTitle(rawTitle, canonicalLink),
        metaDescription = extractMetaDescription(doc),
        metaKeywords = extractMetaKeywords(doc),
        lang = extractLang(doc),
        canonicalLink = canonicalLink,
        openGraphData = OpenGraphData(doc),
        publishDate = publishDate
      )

      val cleanedDoc = DocumentCleaner.clean(doc)
      calculateBestNodeBasedOnClustering(cleanedDoc, lang).map { node =>
        //some mutability beauty
        postExtractionCleanup(node, lang)
        info.copy(cleanedText = Some(node.text()), links = extractLinks(node))
      }.getOrElse(info)
    }
  }

}
