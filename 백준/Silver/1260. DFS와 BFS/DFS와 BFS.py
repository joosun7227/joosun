from collections import deque
n, m, v = map(int, input().split())  # n:정점, m:간선, v:시작점
graph = [[] for _ in range(n + 1)]  # 0번 정점은 사용하지 않으니 1번~n번 정점까지 할당
for i in range(m):
    v1, v2 = map(int, input().split())
    graph[v1].append(v2)
    graph[v2].append(v1)
for i in range(1, n + 1):
    graph[i].sort()  # 순서 보장을 위해 오름차순 정렬 필요
dfs_sol = 0
bfs_sol = 0

# DFS
def dfs(start):
    stack = []
    visit = [0 for _ in range(n + 1)]
    stack.append(start)
    while len(stack) > 0:
        curr = stack[-1]
        if visit[curr] == 0: # 가본적이 없다면
            print(curr, end=' ') 
            visit[curr] = 1 # 가보게 만들고
        flag = False
        for nxt in graph[curr]:
            if visit[nxt] == 0: 
                stack.append(nxt)
                flag = True
                break
        if not flag: #더 갈길이 없으면 pop
            stack.pop()

# BFS
def bfs(start):
    queue = deque()
    visited = [0 for _ in range(n + 1)]
    queue.append(start)
    visited[start] = 1
    while len(queue) > 0:  # queue에 남아있는게 없을때까지 실행
        curr = queue[0] # queue의 첫번째 수 확인
        print(curr, end=' ')
        for nxt in graph[curr]:
            if visited[nxt] == 0:  # 아직 방문 안한 정점만 가야함
                visited[nxt] = 1  # 방문했다고 표시하기
                queue.append(nxt)  # queue에 넣기
        queue.popleft()  # 선입선출
dfs(v) 
print()
bfs(v)