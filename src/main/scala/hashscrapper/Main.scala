package hashscrapper

import java.net.URL
import com.google.common.base.Charsets
import com.google.common.io.Resources

object Main extends App {

  val url = "https://www.znbc.co.zm/news/be-prayerfulsiliya-urges-zambians/"
  val rawHTML = Resources.toString(new URL(url), Charsets.UTF_8)

  val article = HashScrapper.extract(rawHTML)
  println(article.get.cleanedText.get)
}
