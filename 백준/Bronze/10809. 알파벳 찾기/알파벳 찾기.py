import string
abc = list(string.ascii_lowercase)
S = input()
S = list(S)

for i in range(len(abc)):
  if abc[i] in S:
    print(S.index(abc[i]),end=" ")
  elif abc[i] not in S:
    print(-1,end=" ")