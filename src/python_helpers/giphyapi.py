#!/usr/bin/python

import sys
import urllib2
import urllib.request
import json
import urllib.parse

def main(query_string):
    # api info
    base_url = 'http://api.giphy.com/v1/gifs/search?'
    key = {'api_key':'dc6zaTOxFJmzC'}

    # random gif
    random_url = 'http://api.giphy.com/v1/gifs/random?'
    random_url = random_url + urllib.parse.urlencode(key)

    # search gif
    params = key
    params.update({'q' : query_string})
    encoded_params = urllib.parse.urlencode(params)
    full_url = base_url + encoded_params

    # results
    source_urls = []

    # get search by keyword result
    with urllib.request.urlopen(full_url) as response:
        html = response.read()
        data = json.loads(html.decode('utf-8'))
        for entry in data['data']:
            source_urls.append(entry['images']['original']['url'])

    # pick a random gif if no search result is available
    if source_urls == []:
        with urllib.request.urlopen(random_url) as random_response:
            random_html = random_response.read()
            random_data = json.loads(random_html.decode('utf-8'))
            source_urls.append(random_data['data']['image_original_url'])

    print(json.dumps({'data' : source_urls}))
            
if __name__ == "__main__":
    main(' '.join(sys.argv[1:]))
