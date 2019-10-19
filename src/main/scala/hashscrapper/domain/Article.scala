package hashscrapper.domain

import java.util.Date

import hashscrapper.opengraph.OpenGraphData

case class Article(title: String,
                   processedTitle: String,
                   metaDescription: String,
                   metaKeywords: String,
                   lang: Option[String],
                   canonicalLink: Option[String],
                   openGraphData: OpenGraphData,
                   cleanedText: Option[String] = None,
                   links: Seq[Link] = Seq.empty,
                   publishDate: Option[Date] = None)
