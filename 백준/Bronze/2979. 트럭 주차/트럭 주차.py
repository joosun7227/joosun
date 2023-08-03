a,b,c = map(int,input().split())
arrive1,leave1 = map(int,input().split())
arrive2,leave2 = map(int,input().split())
arrive3,leave3 = map(int,input().split())
parking = []
price = 0
for i in range(101):
    parking.append(0)

for i in range(arrive1-1,leave1-1):
    parking[i] += 1
for i in range(arrive2-1,leave2-1):
    parking[i] += 1
for i in range(arrive3-1,leave3-1):
    parking[i] += 1

for i in range(101):
    if parking[i] == 1:
        price += a
    elif parking[i] == 2:
        price += b*2
    elif parking[i] == 3:
        price += c*3

print(price)