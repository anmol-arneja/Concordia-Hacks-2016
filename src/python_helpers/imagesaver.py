import urllib.request, json
import os, sys

def saveImage(url, save_dir):
    save_dir = makeSaveDir(save_dir)
    dot_location = url.rfind('.')
    filename = (save_dir + title.replace('/', ':') + url[dot_location: dot_location + 4]).encode('utf-8')
    if not os.path.exists(filename):
        print('Saving ' , filename , '!\n')
        urllib.request.urlretrieve(url, filename)

def makeSaveDir(dir):
    if not os.path.exists(dir):
        os.makedirs(dir)
    return dir + '/'

def returnImageIDs(code):
    
    i=0
    endlocs = []
    ind = 0
    w = 0
    
    ind = str(code).find('class="post"')
    
    while(ind!=-1):
        
        endlocs += [ind-2]
        ind = str(code).find('class="post"',i+1)
        i = ind
    
    found = False
    
    startlocs = []
    
    for i in endlocs:
        w = i-1
        while(code[w]!='"'):
            w -= 1
        startlocs += [w+1]
        
    if len(startlocs) == 0:
        u = urllib.request.urlopen("http://imgur.com")
        code = str(u.read())   
        return (returnImageIDs(code))
    links = []
    
    for i in range(len(startlocs)):
        links += ["http://i.imgur.com/" + code[startlocs[i]:endlocs[i]] + '.png']
        
    return links
    
def grabImages(term):
    
    u = urllib.request.urlopen("http://imgur.com/search/relevance?q=" + term + "&qs=thumbs")
    
    code = str(u.read())   
    
    r = returnImageIDs(code)
    
    dict = {"data" : r}
    
    encoded = json.dumps(dict)
       
    return encoded
    

if __name__ == '__main__':
    print(grabImages("+".join(sys.argv[1:])))
