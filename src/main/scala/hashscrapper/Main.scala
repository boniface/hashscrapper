package hashscrapper

import java.net.URL
import com.google.common.base.Charsets
import com.google.common.io.Resources

object Main extends App {

  val url = "https://www.znbc.co.zm/news/debt-restructuring-to-end-by-june/"
  val url2 ="https://zambianobserver.com/munir-zulu-needs-to-sober-up-behave-kazabu/"
  val rawHTML = Resources.toString(new URL(url2), Charsets.UTF_8)

  val article = HashScrapper.extract(rawHTML)
  println(article.get.cleanedText.get)
  println(article.get.publishDate)
  println(article.get.title)
  println(article.get.openGraphData.image)

}
