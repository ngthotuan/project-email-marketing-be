
#BASE_URL="https://project-email-marketing-be.nthotuan.repl.co:443"
BASE_URL="http://139.59.232.108:8080"
TOKEN="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbXRlc3QiLCJqdGkiOiJlODFkYzYyZS02NmRlLTRkNTUtYWQzNi0wODkzYjMzYTRkNTEiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDAiLCJpYXQiOjE2NjY1Mjg5ODQsImV4cCI6MTY2NjYxNTM4NH0.uzf5hJItMYyO0K3D6s0gDt7ippbNjFILpDnz416DOSTMGCezquBb1A_LnAUnH5t3ul_hc8d5cYMlLd17VLBYrw"

curl -X POST "${BASE_URL}/email-service/v1/proxies/proxy" -H "accept: */*" -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d "{\"host\":\"154.16.151.43\",\"name\":\"demo proxy\",\"password\":\"l7UHs3ydNuUNdxY856\",\"port\":\"9999\",\"username\":\"tienmop\"}"

curl -X POST "${BASE_URL}/email-service/v1/emails/email" -H "accept: */*" -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d "{\"email\":\"phampham1109@gmail.com\",\"password\":\"nkrjwushgocojvfk\",\"proxyId\":1}"

curl -X POST "${BASE_URL}/email-service/v1/templates/template" -H "accept: */*" -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d "{\"content\":\"tuannt7 test\",\"name\":\"demo template\",\"subject\":\"hello demo\"}"

curl -X POST "${BASE_URL}/email-service/v1/schedules-cronjobs/schedules-cronjob" -H "accept: */*" -H "Authorization: Bearer ${TOKEN}" -H "Content-Type: application/json" -d "{\"email\":\"phampham1109@gmail.com\",\"emailTos\":\"tuannt7.vng@gmail.com,chauxuantuan@gmail.com\",\"scheduleId\":1,\"templateId\":1}"
