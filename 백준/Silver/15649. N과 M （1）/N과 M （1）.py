N,M=map(int,input().split())

n_list=[]
for i in range(1,N+1):
    n_list.append(i)
temp=[]

def backtrack():
    if len(temp)==M:
        print(*temp)
        return
    
    for i in range(N):
        if n_list[i] not in temp:
            temp.append(n_list[i])
            backtrack()
            temp.pop()
backtrack()