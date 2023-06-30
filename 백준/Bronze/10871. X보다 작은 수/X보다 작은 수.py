N, X = map(int, input().split())
A = list(map(int, input().split()))
B = []
for i in A:
    if i < X:
        B.append(i)  # B.append()는 리스트에 원소를 추가하고 None을 반환합니다. 따라서, B = B.append(i)는 잘못된 사용입니다.
B_str = ' '.join(map(str, B))  # 리스트 B의 원소를 문자열로 변환하고 각 원소를 공백으로 구분하여 하나의 문자열로 결합합니다.
print(B_str)
