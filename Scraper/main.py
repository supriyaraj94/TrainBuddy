from scrapy.selector import Selector
from scrapy.http import HtmlResponse
import urllib2
import re

class train_scrape:
	def __init__(self,num):
		self.train_num=num
		#request for the url
		req = urllib2.Request('http://runningstatus.in/status/'+num+'-today')

		#open and read the page/fetch html contents
		res = urllib2.urlopen(req)
		the_page = res.read()

		#create selector object for scraping
		response=Selector(text=the_page)
		
		#store selector object
		self.response=response

		
	def get_status(self):
		#gives info on whether the train is 'running',''Not Started' or 'Arrived'	
		l=self.response.xpath('//div[@class="runningstatus-widget-content"]/p/font/text()').extract()
		status=l[0]
		delay=" ".join(l[1].split()[-2:])
		print(status)
		print(delay)
	
	def get_lastseen(self):
		#Gives the name and time of the last station it has left if status is 'running'
		n=self.response.xpath('//div[@class="runningstatus-widget-content"]/p/b/text()').extract()		
		print(n)

	def print_path(self):
		#Prints the train path
		for row in self.response.xpath('//tbody/tr'):
			col=row.xpath('.//td/text()').extract()
			print(col[0])

	def get_path_as_a_list(self):
		l=[]
		for row in self.response.xpath('//tbody/tr'):
			col=row.xpath('.//td/text()').extract()
			l.append(re.search(r'(.*)[(]',col[0]).group(1))
		return l

	def get_info(self):
		#returns a list with the station name,arrival,departure of all stations along it's path
		l=[]
		for row in self.response.xpath('//tbody/tr'):
			col=row.xpath('.//td/text()').extract()
			d=dict()
			d['station']=(re.search(r'(.*)[(]',col[0]).group(1))
			d['arrival']=col[2]
			d['departure']=col[3]
			l.append(d)
		return l
	

		









obj=train_scrape('12423');
obj.get_status()
obj.get_lastseen()
print(obj.get_path_as_a_list())
print(obj.get_info())

