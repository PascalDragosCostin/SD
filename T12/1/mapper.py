import re
import sys

import urllib.request


def main():

    for line in sys.stdin:
        url_to_discover = line.strip()

        try:
            response = urllib.request.urlopen(url_to_discover)
            html_page = response.read().decode("ISO-8859-1")
            # print(html-page)

            urls = re.findall(r'https?://(?:[-\w.]|(?:%[\da-fA-F]{2}))+', html_page)
            
            for inside_url in urls:
                print('<%s,\t%s>' % (url_to_discover, inside_url))
            
            regexp = r'<a\s+(?:[^>]*?\s+)?href=(["\'])(.*?)\1>'
            # print(regexp)
            
            inside_paths = re.findall(regexp, html_page)
            for inside_path in inside_paths:
                if inside_path[1].startswith("/"):
                    print('<%s,\t%s>' % (url_to_discover, url_to_discover + inside_path[1]))
        except:
            pass


if __name__ == '__main__':
    main()
