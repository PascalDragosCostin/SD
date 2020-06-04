#!/usr/bin/env python3
"""reducer.py"""

import sys



def remove_characters(text, char_sets='<>,'):
    for char in char_sets:
        text = text.replace(char, '')
    return text


def main():
    current_url = None
    links = set()  #declare a set
    for line in sys.stdin:
        line = remove_characters(line.strip())
        # print(line)
        
        url, inside_url = line.split()  # line.split('\t', 1)
        # print(url)
        if current_url != url:
            if len(links) > 0:
                print('<%s,\t%s>\n\n' % (current_url, list(links)))
            current_url = url
            links.clear()

        links.add(inside_url)

    if len(links) > 0:
        print('<%s,\t%s>' % (url, list(links)))


if __name__ == '__main__':
    main()
