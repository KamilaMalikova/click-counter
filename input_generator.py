import datetime as datetime
import random
import sys
import string
lim = 20_000


def main():
    path = input('Path to log file: ')
    N = int(input("Number of files: "))
    R = int(input('Number of records: '))
    X = int(input('Enter X: '))
    Y = int(input('Enter Y: '))
    date_pattern = input('From date (yyyy-MM-dd hh:mm:ss): ')
    date = datetime.datetime.strptime(date_pattern, '%Y-%m-%d %H:%M:%S')
    for j in range(N):
        file_path = path
        if j > 0:
            file_path = path + str(j)
        print(file_path)
        with open(file_path, 'a') as file:
            for i in range(R):
                x = random.randint(a=0, b=X)
                y = random.randint(a=0, b=Y)
                uid = random.randint(0, 10) + 1000
                date += datetime.timedelta(minutes=float(5))
                line = str(x) + ' ' + str(y) + ' ' + str(uid) + ' ' + str(date) + '.00'
                if i % 10000 == 45:
                    line = malformed_str(10)
                    print(line)
                file.write(line + '\n')


def malformed_str(N):
    return ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(N))


if __name__ == "__main__":
    main()
