from scrapy.selector import Selector
from scrapy.http import HtmlResponse
import urllib2
import re
import requests





#give the pnr here
pnr='4519282568'
#get the response page for the url
res=requests.post('http://www.getpnrstatus.co.in?pnrno='+pnr)
#extract the text and create selector object
the_page=res.text
response=Selector(text=the_page)
info=response.xpath('//td/text()').extract()
#print(info)
#starting and destination stations for the given pnr(Full station name)
starting_point=(re.search(r'\][ ](.*)',info[5]).group(1)).strip()
dest_point=(re.search(r'\][ ](.*)',info[6]).group(1)).strip()
train_no=info[1].strip()
#starting and destination station codes for the given pnr
st_abbr=(re.search(r'[[](.*)[]]',info[5]).group(1)).strip()		
dt_abbr=(re.search(r'[[](.*)[]]',info[6]).group(1)).strip()	
#print(st_abbr)
#print(dt_abbr)

#scrape information from a diff url using train number


#request for the url
req = urllib2.Request('http://runningstatus.in/status/'+train_no+'-today')

#open and read the page/fetch html contents
res = urllib2.urlopen(req)
the_page = res.read()

#create selector object for scraping
response=Selector(text=the_page)


#extract info on whether the train is 'running',''Not Started' or 'Arrived'	
l=response.xpath('//div[@class="runningstatus-widget-content"]/p/font/text()').extract()
status=l[0]
delay=" ".join(l[1].split()[-2:])
#print(status+delay)

#extract last_seen
last_seen=response.xpath('//div[@class="runningstatus-widget-content"]/p/b/text()').extract()		
		

#extract train path and store as a list of dictionaries with the station name,arrival,departure of all stations 
train_path=[]
for row in response.xpath('//tbody/tr'):
	col=row.xpath('.//td/text()').extract()
	d=dict()
	d['station_code']=(re.search(r'[(](.*)[)]',col[0]).group(1)).strip()
	d['station']=(re.search(r'(.*)[(]',col[0]).group(1)).strip()
	d['arrival']=col[2]
	d['departure']=col[3]
	try:
		d['expected_arrival']=(re.search(r'([0-9].*)',col[4]).group()).strip()
	except:
		d['expected_arrival']=col[4]
	train_path.append(d)

for stn in train_path:
	if(stn['station_code']==dt_abbr):
		expected_arrival=stn['expected_arrival']




#print(train_path)
print(expected_arrival)#at the destination given in pnr 
print(last_seen)#printed only if the train is currenty running..else empty
print(status)
print(delay)


		

		
		


