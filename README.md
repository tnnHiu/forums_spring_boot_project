# 🚀 Git Workflow
 # Lưu ý: các câu lệnh có thể thay đổi một chút ít
 
## 🧑‍💻 PHẦN 1: Các nhánh khác

### 🔢 BƯỚC 1: Lần đầu clone project (Nếu cần thiết)

```bash
git clone https://github.com/your-team/your-project.git
cd your-project
git checkout your-branch
```

### 🔢 BƯỚC 2: Cập nhật code từ main trước khi bắt đầu làm việc
**Trường hợp:** Mỗi khi bắt đầu một phiên làm việc hoặc trước khi code mới.
```bash
git checkout main
git pull origin main

git checkout your-branch
git merge main
```

### 🔢 BƯỚC 3: Thực hiện code, commit và push lên nhánh cá nhân
**Trường hợp:** Sau khi hoàn thành một phần hoặc toàn bộ tính năng.
```bash
git add .
git commit -m "Mô tả nội dung cập nhật"
git push your-branch
```

### 🔢 BƯỚC 4: Tạo Pull Request từ nhánh cá nhân lên main
**Trường hợp:** Khi hoàn thành tính năng hoặc cần review code.
- Truy cập GitHub → Pull Requests → New Pull Request
- Base: `main`, Compare: `your-branch`
- Ghi chú nội dung và gửi cho phía main review

### 🔢 BƯỚC 5: Cập nhật main nếu đang chờ review và main có thay đổi
**Trường hợp:** Pull Request đang pending, nhưng Manager đã merge PR khác.
```bash
git checkout main
git pull origin main

git checkout your-branch
git merge origin/main
```

👉 **Lưu ý:** Nếu đang code dang dở mà main thay đổi:
1. Tạm commit phần code đang làm (nếu chưa commit).
2. Thực hiện cập nhật main như BƯỚC 5.
3. Xử lý conflict nếu có (Xem BƯỚC 6).
4. Sau khi ổn định, tiếp tục code.

### 🔢 BƯỚC 6: Fix conflict nếu có trong quá trình merge main vào nhánh đang làm việc
**Trường hợp:** Merge main vào nhánh đang làm việc bị conflict.
- Mở file bị conflict → Sửa tay → Save lại
```bash
git add .
git commit -m "Fix conflict từ main"
git push origin your-branch
```

### 🔢 BƯỚC 7: Sau khi Pull Request được merge vào main
**Trường hợp:** Tiếp tục làm việc mới.
```bash
git checkout main
git pull origin main

git checkout your-branch
git merge origin/main
```
---

## 👩‍💼 PHẦN 2: QUY TRÌNH CHO MAIN

### 🔢 BƯỚC 1: Review và merge Pull Request
**Trường hợp:** Nhánh khác đã gửi PR, Main sẽ kiểm tra logic, code style, bug.
- Truy cập GitHub → Pull Requests → Review → Merge nếu đạt yêu cầu
```bash
git checkout main
git pull origin main
```

### 🔢 BƯỚC 2: Kiểm tra code trước khi merge (nếu cần)
**Trường hợp:** Muốn test code DEV trước khi merge.
```bash
git fetch origin dev-yourname
git checkout dev-yourname
```
