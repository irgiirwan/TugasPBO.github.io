package CRUD1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;

public class Database {

    //field
    // vector data mahasiswa
    private Vector<Mahasiswa> data = new Vector<>();
    // nama file dan lokasi file
    private String filename = "src/CRUD1/data.csv";
    // membuat path dari filename
    private Path path = Path.of(filename);

    public Database() {
        open();
    }

    public Vector<Mahasiswa> getData() {
        return data;
    }

    // menampilkan menu pilihan
    public void menuPilihan() {
        System.out.println();
        System.out.println("+===============+");
        System.out.println("|  Pilih Menu:  |");
        System.out.println("+---------------+");
        System.out.println("|  [C] : Create |");
        System.out.println("|  [R] : Read   |");
        System.out.println("|  [U] : Update |");
        System.out.println("|  [D] : Delete |");
        System.out.println("|  [X] : Exit   |");
        System.out.println("+===============+");
    }

    // membuka dan mengirimkan data di file csv database ke Vector<Mahasiswa> data (meyimpan data dari data.csv ke vector)
    public void open() {
        //dibungkus dengan try catch
        try {
            // memcara semua baris data
            List<String> lines = Files.readAllLines(path);
            // inisialisasi ulang
            data = new Vector<>();
            // memulai dari 1 agar tidak membaca header
            for (int i = 1; i < lines.size(); i++) {
                // membaca barisnya (buat satu baris)
                String line = lines.get(i);
                // jadikan split elemen (simbol pemisah ",")
                String[]element = line.split(",");
                // index mulai dari 0 karena ini array
                String nim = element[0];
                String nama = element[1];
                String alamat = element[2];
                int semester = Integer.parseInt(element[3]);
                int sks = Integer.parseInt(element[4]);
                double ipk = Double.parseDouble(element[5]);
                // membuat objek dari kelas Mahasiswa (dan memanggil constructornya)
                Mahasiswa mhs = new Mahasiswa(nim,nama,alamat,semester,sks,ipk);
                // objek ditambahkan ke dalam vector
                data.add(mhs);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Mengirim Data ke file csv Database (menyimpan data dari vector ke data.csv)
    public void save() {
        // membaca satu per satu (per baris) elemen dari vector
        StringBuilder sb = new StringBuilder();
        // mengisi pada baris pertama sebagai header
        sb.append("NIM,NAMA,ALAMAT (KOTA),SEMESTER,SKS,IPK\n");
        // membaca data (jika data vector tidak kosong (karena menggunakan NOT "!"))
        if(!data.isEmpty()) {
            // membaca data satu per satu elemen data dari vector
            for (int i = 0; i < data.size(); i++) {
                Mahasiswa mhs = data.get(i);
                // reverse menjadi teks dan menggunakan pemisah yang sama ","
                String line = mhs.getNim()+","+mhs.getNama()+","+mhs.getAlamat()+","+mhs.getSemester()+","+mhs.getSks()+","+mhs.getIpk()+"\n";
                // kita append
                sb.append(line);
            }
            try {
                // tempat menuliskan data ke data.csv
                Files.writeString(path, sb.toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // menampilkan data
    public void read() {
        // membaca data (jika data vector tidak kosong (karena menggunakan NOT "!"))
        if(!data.isEmpty()) {
            System.out.println("==================================================================================");
            System.out.printf("| %-8.8s |","NIM");
            System.out.printf(" %-20.20s |","NAMA");
            System.out.printf(" %-20.20s |","ALAMAT");
            System.out.printf(" %8.8s |","SEMESTER");
            System.out.printf(" %3.3s |","SKS");
            System.out.printf(" %4.4s |%n","IPK");
            System.out.println("----------------------------------------------------------------------------------");
            // menggunnakan looping untuk menelusuri data
            for (Mahasiswa mhs : data) {
                // isi looping membaca data mahasiswa di vector, kemudia di cetak dengan format
                System.out.printf("| %-8.8s |",mhs.getNim());
                System.out.printf(" %-20.20s |",mhs.getNama());
                System.out.printf(" %-20.20s |",mhs.getAlamat());
                System.out.printf(" %8.8s |",mhs.getSemester());
                System.out.printf(" %3.3s |",mhs.getSks());
                System.out.printf(" %4.4s |%n",mhs.getIpk());
            }
            System.out.println("----------------------------------------------------------------------------------");
        }
    }

    // Menambahkan dan Menyimpan Data ke Vector<Mahasiswa> data
    public boolean insert(String nim, String nama, String alamat, int semester, int sks, double ipk) {
        // status awal
        boolean status = true;
        // membaca data (jika data vector tidak kosong (karena menggunakan NOT "!"))
        if(!data.isEmpty()) {
            // cek semua baris data
            for (int i = 0; i < data.size(); i++) {
                // cara pengecekan 2
                if(data.get(i).getNim().equalsIgnoreCase(nim)) {
                    status = false;
                    break;
                }
            }
        }
        // data di tambahkan jika nim belum ada / tidak terdaftar
        if(status == true) {
            Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
            data.add(mhs);
            save();
        }
        return status;
    }

    // fitur method untuk pencatian nim di database
    public int search(String nim) {
        int index = -1;
        // membaca data (jika data vector tidak kosong (karena menggunakan NOT "!"))
        if(!data.isEmpty()) {
            // cek semua baris data
            for (int i = 0; i < data.size(); i++) {
                // pengecekan nim
                if(data.get(i).getNim().equalsIgnoreCase(nim)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    // Menambahkan dan Menyimpan Data ke Vector<Mahasiswa> data
    public boolean update(int index, String nim, String nama, String alamat, int semester, int sks, double ipk) {
        boolean status = false;
        if(!data.isEmpty()) {
            if(index >= 0 && index < data.size()) {
                // membuat objek baru
                Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
                // untuk mengupdate data sesuai input index dengan objek baru
                data.set(index,mhs);
                status = true;
                // datanya berubah maka segera di save
                save();
            }
        }
        return status;
    }

    public boolean delete(int index) {
        boolean status = false;
        // membaca data (jika data vector tidak kosong (karena menggunakan NOT "!"))
        if(!data.isEmpty()) {
            // mencoba untuk hapus sesuai dengan index
            data.remove(index);
            // segera di save
            save();
            status = true;
        }
        return status;
    }
}
