import sys

def main():
    current_url = None
    current_count = 0
    print('{"info":[')
    for line in sys.stdin:
        line = line.strip()

        url, count = line.split()  # line.split('\t', 1)

        try:
            count = int(count)
        except ValueError:
            continue

        if current_url != url:
            if current_url:
                print('\t{"%s" : %s},' % (current_url, current_count))
            current_count = count
            current_url = url
        else:
            current_count += count

    if current_count > 0:
        print('{"%s":%s}' % (current_url, current_count))
        
    print("]}")
if __name__ == '__main__':
    main()