package CRUD1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;

public class Database {

    private Vector<Mahasiswa> data = new Vector<>();
    private String filename = "src/CRUD1/data.csv";
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

    // membuka dan mengirimkan data di file csv database ke Vector<Mahasiswa> data
    public void open() {
        try {
            List<String> lines = Files.readAllLines(path);
            data = new Vector<>();
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[]element = line.split(",");
                String nim = element[0];
                String nama = element[1];
                String alamat = element[2];
                int semester = Integer.parseInt(element[3]);
                int sks = Integer.parseInt(element[4]);
                double ipk = Double.parseDouble(element[5]);
                Mahasiswa mhs = new Mahasiswa(nim,nama,alamat,semester,sks,ipk);
                data.add(mhs);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // menampilkan data
    public void read() {
        if(!data.isEmpty()) {
            System.out.println("==================================================================================");
            System.out.printf("| %-8.8s |","NIM");
            System.out.printf(" %-20.20s |","NAMA");
            System.out.printf(" %-20.20s |","ALAMAT");
            System.out.printf(" %8.8s |","SEMESTER");
            System.out.printf(" %3.3s |","SKS");
            System.out.printf(" %4.4s |%n","IPK");
            System.out.println("----------------------------------------------------------------------------------");
            for (Mahasiswa mhs : data) {
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
        boolean status = true;
        if(!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getNim().equalsIgnoreCase(nim)) {
                    status = false;
                    break;
                }
            }
        }
        if(status == true) {
            Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
            data.add(mhs);
            save();
        }
        return status;
    }

    // Menambahkan dan Menyimpan Data ke Vector<Mahasiswa> data
    public boolean update(int index, String nim, String nama, String alamat, int semester, int sks, double ipk) {
        boolean status = false;
        if(!data.isEmpty()) {
            if(index >= 0 && index < data.size()) {
                Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
                data.set(index,mhs);
                status = true;
                save();
            }
        }
        return status;
    }

    // Mengirim Data ke file csv Database
    public void save() {
        StringBuilder sb = new StringBuilder();
        sb.append("NIM,NAMA,ALAMAT (KOTA),SEMESTER,SKS,IPK\n");
        if(!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                Mahasiswa mhs = data.get(i);
                String line = mhs.getNim()+","+mhs.getNama()+","+mhs.getAlamat()+","+mhs.getSemester()+","+mhs.getSks()+","+mhs.getIpk()+"\n";
                sb.append(line);
            }
            try {
                Files.writeString(path, sb.toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int search(String nim) {
        int index = -1;
        if(!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getNim().equalsIgnoreCase(nim)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public boolean delete(int index) {
        boolean status = false;
        if(!data.isEmpty()) {
            data.remove(index);
            save();
            status = true;
        }
        return status;
    }
}
