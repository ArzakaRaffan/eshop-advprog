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

