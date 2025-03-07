# Eshop Advanced Programming
### Arzaka Raffan Mawardi - 2306152393
---
## Tutorial 1
---
### Refleksi 1
> You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you find any mistake in your source code, please explain how to improve your code.

#### Source code yang saya tuliskan sudah mengikuti clean code principles dan beberapa secure coding practices seperti:
- Menggunakan prinsip meaningful names dalam penamaan variabel, function, class, argumen, dan yang lainnya. Penamaan tersebut bermakna jelas dan cukup menjelaskan seperti variabel productQuantity, productName dan function-function seperti findByID, create, delete, dan lain-lain.
- Menggunakan prinsip penggunaan function hanya untuk satu task seperti function findByID yang hanya digunakan untuk mencari sebuah produk berdasarkan ID.
- Menggunakan comments pada beberapa code yang bersifat explanatory comments. Contohnya pada salah satu unit-test saya yang bertujuan untuk mengedit product yang tidak ada sebelumnya, saa menambahkan comments tambahan sebagai penjelasan singkat
- Menggunakan beberapa input validation seperti pada input nama produk yang tidak boleh null atau kosong dan input kuantitas produk yang tidak boleh nol atau bernilai negatif.

Beberapa kesalahan yang saya temukan dalam source code saya salah satunya adalah tidak adanya ID Produk yang digenerate secara otomatis. Hal ini berakibat sebuah produk tidak dapat secara unik diperoleh dari list product. Oleh karena itu, saya membuat ID Product yang digenerate secara otomatis setiap kali product dibuat dengan menggunakan UUID. Input validation mengenai nama produk yang null atau kosong dan kuantitas produk yang nol atau bernilai negatif juga belum di-handle, sehingga saya harus menerapkan beberapa input validation tersebut. Hal ini diperlukan agar tidak ada data-data yang tidak masuk akal.

### Refleksi 2
> After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors? 

Dalam sebuah class, jumlah unit test tergantung dari function yang dimiliki oleh setiap class. Setiap function tersebut minimal memiliki 1 unit test yang bertanggungjawab menjalankan fitur penting yang dimiliki oleh function tersebut. Namun, best practice nya adalah per function dibuat unit test untuk case positive dan case negative nya. Contohnya pada edit product, unit test yang bertanggung jawab untuk case positive adalah test edit product yang sudah ada sebelumnya sementara case negative nya adalah test edit product jika product tidak ditemukan. Kemudian cara untuk memastikan unit test dapat memverifikasi program adalah dengan membuat unit test yang meng-cover semua case dari sebuah function. 100% code coverage belum tentu menunjukkan bahwa kode bebas dari bug dan error, bisa saja terdapat beberapa case-case yang belum di-handle oleh unit test.

> Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

Pembuatan Java class yang sama dengan functional test utama tersebut dapat mengurangi code cleanliness karena adanya duplikasi dan redundansi kode. Solusi yang dapat dilakukan adlaah dengan membuat superclass yang berisi Set Up umum dan menggunakannya di dalam subclass yang membutuhkan Set Up umum tersebut. Dengan hal tersebut, duplikasi dan redundansi kode yang menyebabkan berkurangnya code cleanliness dapat dicegah.

---
# Tutorial 2
---
### Refleksi
> List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
- Menambahkan izin yang sesuai di dalam `ci.yml` berupa `permissions: contents: read` yang mengatur _token permission_ minimum dari sebuah GITHUB_TOKEN
- Menambahkan _Step Security Harden-Runner_ dalam _workflow_ GitHub Actions untuk mengaudit semua koneksi keluar (_outbound calls_) dari runner yang menjalankan workflow pada file `pmd.yml` dan juga `scorecard.yml`.
- Menambahkan branch protection pada branch _master_ dengan menambahkan pelarangan deletion dan juga mencegah _force-push_ pada branch _master_.
- Menggunakan hash commit pada _upload artifact_ dan juga _upload to code-scanning_ pada `scorecard.yml` untuk menghindari perubahan tak terduga pada action sehingga lebih aman dan stabil.
- Menghapus import yang tidak digunakan atau tidak diperlukan yakni `import org.springframework.web.bind.annotation.*;`, menjadi import hal-hal yang digunakan saja dari satu package tersebut
- Menghapus modifier public dari method method di `ProductService.java`, karena secara default java memberikan modifier public dan tidak perlu state ulang.

> Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
Menurut saya, CI/CD Workflow yang saya buat sudah memenuhi definisi _Continuous Integration and Continuous Deployment_. Continuous Integration saya terletak pada file `ci.yml` yang melakukan build dan unit test secara otomatis setiap kali terdapat push dan juga pull request. Kemudian saya juga mengintegrasikan `Scorecard` dan `PMD` untuk tetap mengevaluasi security issue yang muncul setiap kali ada perubahan dalam kode. Lalu dalam Continuous deployment, saya menggunakan service _koyeb_ yang akan melakukan deployment setiap kali ada push atau pull di branch _master_.

---
# Tutorial 3
---
> Explain what principles you apply to your project!
- **Single Responsibility Principle (SRP)**: Principle ini saya implementasikan dengan memastikan setiap kelas hanya memiliki satu tanggung jawab saja. Modifikasi yang saya buat untuk menerapkan SRP ini adalah dengan memisahkan kelas `ProductController` dengan kelas `CarController` dimana masing-masing kelas hanya menangani tanggung jawab dan fungsi terkait HTTP mengenai Product dan Car secara terpisah.
- **Open-Closed Principle (OCP)**: Implementasi OCP saya lakukan dengan memungkinkan adanya ekstensi tanpa mengubah dan memodifikasi class tersebut. Contoh yang saya implementasikan adalah pada `CarService`, dimana interface tersebut diimplementasikan pada kelas yang berbeda yakni `CarServiceImpl` tanpa mengubah apa yang terdapat pada `CarService` itu sendiri.
- **Liskov Subtitution Principle (LSP)**: LSP memungkinkan subclass untuk 'menggantikan' superclass tanpa mengganggu fungsionalitas dari superclass tersebut. `CarServiceImpl` pada kode saya meng-override method-method yang ada di `CarService` tanpa sama sekali mengganggu class `CarService` sehingga kelas tersebut dapat dipakai dimanapun kelas tersebut dibutuhkan.
- **Interface Segregation Principle (ISP)**: Interface yang lebih besar dipecah menjadi interface yang lebih kecil. Pada kode saya, saya memisahkan interface untuk Product dan Car sehingga tidak menjadi satu interface yang sama. Menjadikan kode lebih baik karena masing-masing interface bertanggung jawab pada tugas dengan scope yang lebih kecil.
- **Dependency Inversion Principle (DIP)**: dalam Principle DIP ini, class-class yang ada dalam kode seharusnya dependent terhadap abstract class, bukan concrete class. Pengimplementasian dalam kode saya adalah mengakses method-method dalam `CarServiceImpl` melalui interface yang merupakan abstract class pada `CarController`, tidak secara langsung mengakses concrete class dari `CarServiceImpl`.

> Explain the advantages of applying SOLID principles to your project with examples
- **Maintainability**: Kemudahan dalam pemeliharaan atau maintainability didapatkan karena class-class sudah dipisah berdasarkan tanggung jawabnya masing-masing. Misalkan jika terdapat perubahan pada logika method-method Car, maka yang perlu diubah hanya `CarServiceImpl` dan tidak perlu hingga mengubah `CarRepository`.
- **Lower Risk of Bugs**: Pengimplementasian OCP memudahkan developer untuk mengubah dan juga menambahkan sebuah fitur tanpa perlu takut terdapat bug yang terjadi di kelas yang tidak dimdofikasi. Hal ini dapat terjadi karena kode sudah menerapkan ekstensi terhadap class-class sehingga tidak akan mempengaruhi sisi lain program. Contohnya developer hanya perlu mengubah `CarServiceImpl` tanpa mengubah `CarService` karena terdapat ekstensi yang jelas.
- **Testability**: Pemisahan yang jelas terhadap logika bisnis program memudahkan tester untuk melakukan debugging dan testing karena kesalahan pada testing dapat diketahui letaknya dengan mudah. Contohnya terdapat kegagalan akses sebuah page saat testing, tester akan tahu kesalahn pasti terletak pada controller dan method mana yang terdampak.

> Explain the disadvantages of not applying SOLID principles to your project with examples.
- **Unexpected behavior**: Tidak adanya pemisahan tanggung jawab yang jelas dalam subclass dapat menyebabkan perubahan yang tidak diekspektasikan untuk terjadi dalam superclass. Contohnya sebelum penerapan Single Responsibility Principle (SRP), `CarController` dapat saja mengakses data dalam `ProductController` jika menggunakan nama yang sama, padahal mereka berdua menangani dua objek yang tidak ada hubungannya.
- **Unnecesary Dependencies**: Pada kode awal `CarController` men-extends `ProductController`, hal ini harus dipisahkan logikanya karena dua hal tersebut berbeda. Dependencies yang tidak perlu ini dapat mempersulit code maintainability.
- **Code Duplication**: Tanpa penerapan SOLID, sebuah concrete class atau abstract class cenderung memiliki scope yang besar sehingga memungkinkan adanya duplikasi kode yang tidak perlu. Pemisahan antara `CarController`, `CarRepository`, dan juga `CarService` dapat mereduksi kemungkinan duplikasi kode.

---
# Tutorial 4
---
> Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.
Flow dari TDD ini cukup berguna untuk dapat memperjelas kegunaan sebuah kode dan membantu dalam mendefinisikan perilaku yang diharapkan dengan jelas karena dibuat dengan pengujian (_testing_) terlebih dahulu. Jika setelah pengimplementasian class masih mengalami beberapa kegagalan _testing_, berarti TDD berjalan secara efektif dimana TDD dapat memberitahu di bagian mana bagian class yang masih kurang sesuai. Namun terdapat beberapa kesulitan yang saya pribadi rasakan saat menerapkan TDD Flow ini, salah satunya adalah beberapa kasus yang terlewat dalam tests ketika saya sedang mengimplementasikan sebuah class. Hal ini membuat saya harus memperbaiki cakupan test saya menjadi lebih luas lagi. Lalu karena saya baru menggunakan TDD dan tidak banyak berurusan dengan unit tests sebelumnya, saya masih sering gagal dalam membuat suatu desain pengujian.

> You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.
Dalam kode saya beberapa prinsip dari F.I.R.S.T. sudah saya implementasikan dengan baik dan sebagian belum dapat saya implementasikan. Fast (F), sudah saya implementasikan karena dalam test saya, saya hanya mengandalkan memori dan tidak menggunakan dependencies eksternal yang dapat memperlambat jalannya program. Independent (I), berhasil saya implementasikan via `@BeforeEach` yang membuat satu tests dengan tests lain bersih dan tidak tergantung satu sama lain. R (Repeatable), saya implementasikan dengan berhasil menjalankan tests baik di lokal maupun di bagian CI (Continuous Integration). Self-Validating (S) saya implementasikan melalui test-test assertion (`assertThrows`, `assertNull`, `assertEquals`) yang melakukan validasi ulang beberapa hal. Terakhir, Timely (T) saya implementasikan melalui pembuatan testing yang saya lakukan terlebih dahulu lalu mengimplementasikan class nya. Lalu tests saya juga sudah meng-cover semua kemungkinan error, results, happy paths, dan unhappy paths.
