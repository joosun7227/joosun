word = str(input())
word1 = list(word)
word2 = list(word)
word2.reverse()

if word1 == word2:
  print("1")
else:
  print("0")