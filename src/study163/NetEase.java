package study163;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class NetEase {


		//private static Logger logger = LoggerFactory.getLogger(NetEasePageProcesser.class);
		protected static List<String> getlist(String url){
			List<String> list = new ArrayList<String>();
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setCssEnabled(false);
			try {
				HtmlPage htmlPage = webClient.getPage(url);
				//System.out.println(htmlPage.asXml());
				//获取课程链接
				DomNodeList<DomElement> elements = htmlPage.getElementsByTagName("a");
				for(DomElement element : elements){
					//String classAttrName = "j-href f-pr";
					if(element.getAttribute("class").equals("link f-f0 nowrp f-cf")){
						list.add(element.getAttribute("href"));
					}
				}
				
				//获取信息
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//logger.error(e.getMessage(),e);
				System.out.println("无法获取页面");
			}
			return list;
		}
		
		protected static List<String> getsublist(String url){
			List<String> list = new ArrayList<String>();
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setCssEnabled(false);
			try {
				HtmlPage htmlPage = webClient.getPage(url);
				//System.out.println(htmlPage.asXml());
				//获取课程链接
				DomNodeList<DomElement> elements = htmlPage.getElementsByTagName("a");
				for(DomElement element : elements){
					//String classAttrName = "j-href f-pr";
					if(element.getAttribute("class").equals("u-cover")){
						list.add(element.getAttribute("href"));
					}
				}
				
				//获取信息
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//logger.error(e.getMessage(),e);
				System.out.println("无法获取页面");
			}
			return list;
		}
		
		public static void main(String[] args){
			String mainDomain = "http://open.163.com";
			List<String> list = getlist(mainDomain);
			try {
				FileOutputStream fo =new FileOutputStream(new File("D:/open163.txt"));
				PrintStream p = new PrintStream(fo);
				for(int i = 0;i<list.size();i++){
					List<String> subList = getsublist(list.get(i));
					for(int j =0 ;j<subList.size();j++){
						//process(subList.get(j));
						WebClient webClient = new WebClient(BrowserVersion.CHROME);
						webClient.getOptions().setJavaScriptEnabled(false);
						webClient.getOptions().setCssEnabled(false);
						try {
							HtmlPage htmlPage = webClient.getPage(subList.get(j));
							//System.out.println(htmlPage.asXml());
							//获取课程链接
							DomNodeList<DomElement> elements = htmlPage.getElementsByTagName("div");
							for(DomElement element : elements){
								//String classAttrName = "j-href f-pr";
								if(element.getAttribute("class").equals("m-cdes")){
									p.println(element.getChildNodes().get(1).getTextContent());
									p.println(element.getChildNodes().get(9).getTextContent());
									break;
								}
							}
							
							DomNodeList<DomElement> teacher = htmlPage.getElementsByTagName("h6");
							for(DomElement element : teacher){
								p.println(element.getTextContent());
							}
							
							
							DomNodeList<DomElement> lesson = htmlPage.getElementsByTagName("td");
							for(DomElement element : lesson){
								if(element.getAttribute("class").equals("u-ctitle")){
									p.print(element.getTextContent());
									//System.out.println(element.getChildNodes().get(1).getTextContent());
									 for(DomElement tag : element.getChildElements()){
										p.print(tag.getAttribute("href"));
									}
								}
							}
							p.println(" ");
							p.println(" ");
							p.println(" ");
							p.println(" ");
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//logger.error(e.getMessage(),e);
							System.out.println("无法获取页面");
						}
						//return list;
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

	}

