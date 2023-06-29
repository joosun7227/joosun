# 3정수를 입력
a,b,c = map(int,input().split())

# 같은눈이 3개가 나올 경우
if a == b == c :
  print(10000 + a*1000)
# 같은 눈이 2개가 나올 경우
elif a == b :
  print(1000 + a*100)
elif b == c :
  print(1000 + b*100)
elif a == c :
  print(1000 + a*100)
# 같은 눈이 다른 경우
else :
  # a > b를 비교
  if a > b :
    if c > a :
      print(c*100)
    elif c < a :
      print(a*100)
  # b > a를 비교
  elif b > a :
    if b > c :
      print(b*100)
    elif b < c :
      print(c*100)
  # b > c를 비교
  elif b > c :
    if a > b :
      print(a*100)
    elif a < b :
      print(b*100)
  # c > b를 비교
  elif b < c :
    if a > c :
      print(a*100)
    elif a < c :
      print(c*100)
  # a > c를 비교
  elif a > c :
    if a > b :
      print(a*100)
    elif a < b :
      print(b*100)
  # c > a를 비교
  elif a < c :
    if c > b :
      print(c*100)
    elif c < b :
      print(b*100)