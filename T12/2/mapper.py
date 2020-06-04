import re
import sys

import sqlite3
from sqlite3 import Error


def main():
    database = sys.stdin.readlines()[0].strip()
    conn = None
    try:
        conn = sqlite3.connect(database)
    except Error as e:
        sys.exit()

    cur = conn.cursor()
    cur.execute("SELECT url, frecency FROM moz_places")
    rows = list(cur.fetchall())
    cur.close()
    
    for link, frequency in rows:
        extract_url = re.search("(https?://(?:[-\w.])+)/?", link)
        if extract_url and frequency and frequency > 0:
            print('%s\t%s' % (extract_url.group(1), frequency))
    conn.close()


if __name__ == '__main__':
    main()
