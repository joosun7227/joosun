n,m = map(int,input().split(' '))
mat1 = [] #첫번째 입력받는 매트릭스
mat2 = [] #두번째 입력받는 매트릭스
mat3 = [] #출력 하는 매트릭스

for row in range(n):
    # 변수 aa에 리스트 형태로 데이터를 입력받음
    aa = list(map(int,input().split()))
    # 변수 aa에 저장된 데이터 변수를 mat1에 붙여넣는다.
    mat1.append(aa)

for row in range(n):
    bb = list(map(int,input().split()))
    mat2.append(bb)

for row in range(n):
    for col in range(m):
        # 리스트 자료형 변수인 mat3에 행렬 mat1와 mat2를 더한 값을 넣어준다.
        mat3.append(mat1[row][col] + mat2[row][col])
        # 행 단위로 데이터를 출력해야 하므로 행 반복문에서 출력해준다.
        # 이때 리스트 자료형 변수 앞에 *을 붙이면 대괄호 없이 한번에 리스트 데이터를 출력해준다.

for i in range(n):
    print(*mat3[i * m:(i + 1) * m])