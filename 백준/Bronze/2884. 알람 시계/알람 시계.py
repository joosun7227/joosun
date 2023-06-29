h,m = map(int,input().split())
m = m - 45
if m >= 0 and h >= 1 :
    print(h, m)
elif m >= 0 and h < 1 :
    print(h, m)
elif m < 0 and h >= 1 :
    m = 60 + m
    h = h - 1
    print(h, m)
else :
    m = 60 + m
    h = 23
    print(h, m)