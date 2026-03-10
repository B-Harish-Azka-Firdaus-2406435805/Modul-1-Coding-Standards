## Reflection Module 1

Pada latihan ini, saya mengimplementasikan dua fitur baru di aplikasi Spring Boot, yaitu Edit Product dan Delete Product. Kedua fitur ini mengikuti arsitektur berlapis (Controller, Service, Repository, dan Template) yang sudah ada sebelumnya. Lewat proses ini saya jadi lebih paham bagaimana alur kerja tiap layer dan tanggung jawab masing-masing.

Dari sisi coding standards, ada beberapa prinsip clean code yang saya coba terapkan:

* Separation of Concerns — logika bisnis ada di Service layer, pengelolaan data di Repository, dan penanganan request di Controller.
* Penamaan class, method, dan variabel dibuat konsisten dan deskriptif supaya kode lebih mudah dibaca.
* Setiap method punya satu tanggung jawab saja, jadi fungsinya jelas dan tidak melakukan terlalu banyak hal sekaligus.
* Template HTML menggunakan Thymeleaf dibuat sederhana dan terhubung langsung dengan backend.

Untuk secure coding practices, walaupun aplikasi ini belum pakai database, saya tetap menjaga agar logika internal tidak diekspos langsung ke client. Data diproses lewat Service layer dulu sebelum ke Repository, jadi alur datanya tetap terkontrol. Struktur MVC juga membantu membatasi akses langsung ke data.

Saya juga belajar soal manajemen versi pakai Git di latihan ini. Dengan memisahkan pengembangan fitur ke branch `edit-product` dan `delete-product`, saya bisa mengerjakan fitur secara terisolasi tanpa mengganggu branch utama. Waktu merge sempat ada konflik, dan dari situ saya dapat pengalaman langsung menangani konflik kode.

Beberapa hal yang masih bisa ditingkatkan ke depannya:

* Menambahkan validasi input yang lebih ketat untuk mencegah data tidak valid.
* Menggunakan unit testing untuk memastikan setiap fitur berjalan sesuai harapan.
* Mengintegrasikan database persistence agar data tidak hilang saat aplikasi dihentikan.

Secara keseluruhan, latihan ini membantu saya memahami penerapan clean code, secure coding, dan workflow Git dalam pengembangan aplikasi Spring Boot.



## Reflection Module 3

## 1. Principles Applied

### SRP (Single Responsibility Principle)

Each class has one responsibility:

- `Car` → Data model
- `CarRepository` → Data storage
- `CarService` → Business logic
- `CarController` → HTTP request handling
- `CarIdGenerator` → ID generation

---

### OCP (Open Closed Principle)

`CarRepository` implements an interface (`CarRepositoryInterface`).  
This allows us to extend repository implementations (e.g., DatabaseCarRepository)  
without modifying the service layer.

---

### LSP (Liskov Substitution Principle)

`CarServiceImpl` properly implements `CarService`.  
It can replace the interface without breaking system behavior.

---

### ISP (Interface Segregation Principle)

`CarService` contains only methods relevant to car operations.  
No unnecessary or unused methods are included.

---

### DIP (Dependency Inversion Principle)

`CarServiceImpl` depends on `CarRepositoryInterface` (abstraction),  
not on the concrete `CarRepository` class.

This reduces tight coupling between layers.

---

## 2. Advantages of Applying SOLID

- Easier maintenance
- Easier testing (repository can be mocked)
- Easier extension (e.g., switch to database implementation)
- Lower coupling
- Cleaner architecture

**Example:**  
If we switch from in-memory `List` storage to a database,  
we only change the repository implementation without modifying the service layer.

---

## 3. Disadvantages of NOT Applying SOLID

Without SOLID:

- Tight coupling
- Hard to test
- Hard to extend
- Higher risk of breaking other components
- Leads to messy / spaghetti architecture

**Example:**  
If `CarService` depends directly on `CarRepository` (concrete class),
any change in repository implementation will affect the service layer.

---

## Reflection Module 4

### 1. TDD Flow Reflection (based on Percival 2017)

Following the Red-Green-Refactor TDD cycle in this exercise was genuinely useful. Writing tests first forced me to think about the expected behavior before writing any implementation, which helped clarify requirements (e.g., what constitutes a valid voucher code, when a status change should be rejected). The failing tests acted as a clear specification. When all tests passed after implementing the logic, I had concrete evidence that the code behaved correctly.

However, I noticed that the TDD flow is most valuable when requirements are well-defined upfront. For the Payment service, I had to revisit some tests after realizing edge cases I missed initially (e.g., null vs empty string for Cash on Delivery fields). This aligns with Percival's point that TDD works best when paired with careful requirement analysis — the tests should cover both happy and unhappy paths thoroughly before implementation begins. Next time, I should spend more time identifying all edge cases during the RED phase before writing implementation.

### 2. F.I.R.S.T. Principle Reflection

Reviewing the unit tests created in this tutorial against the F.I.R.S.T. principle:

- **Fast**: The tests run quickly since they use in-memory data structures and Mockito mocks — no actual database or network calls. ✅
- **Independent**: Each test uses `@BeforeEach` to reset state, ensuring tests don't depend on each other's outcomes. ✅
- **Repeatable**: Tests produce the same result regardless of environment or execution order since no external dependencies exist. ✅
- **Self-Validating**: All tests use JUnit assertions (`assertEquals`, `assertThrows`, `assertNull`, etc.) that clearly pass or fail. ✅
- **Timely**: Tests were written before implementation code as part of the TDD cycle, not after the fact. ✅

Overall, the tests follow F.I.R.S.T. well. One area for improvement is that some test method names could be more descriptive about the exact scenario being tested, making test failure messages easier to diagnose at a glance.