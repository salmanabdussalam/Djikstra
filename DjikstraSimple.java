import java.util.*; // Mengimpor semua class dari package java.util

// Kelas utama
public class DjikstraSimple {

    // Kelas untuk merepresentasikan sisi (Edge) dari graf
    static class Edge {
        int target, bobot; // target = simpul tujuan, bobot = jarak/waktu dari sisi

        Edge(int target, int bobot){
            this.target = target;
            this.bobot = bobot;
        }
    }

    // Kelas Node untuk digunakan dalam priority queue (untuk menyimpan simpul & jaraknya)
    static class Node implements Comparable<Node> {
        int vertex, jarak;

        Node(int v, int j) {
            vertex = v;
            jarak = j;
        }

        // Metode untuk membandingkan jarak antar simpul, digunakan oleh priority queue
        public int compareTo(Node other) {
            return Integer.compare(this.jarak, other.jarak);
        }
    }

    // Fungsi utama
    public static void main(String[] args) {
        int n = 5; // Jumlah simpul dalam graf

        // Inisialisasi graf sebagai adjacency list
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>()); // Tambahkan list kosong untuk tiap simpul

        // Menambahkan sisi (graf tidak berarah, jadi tambahkan dua arah)
        addEdge(graph, 0, 1, 4);
        addEdge(graph, 0, 2, 1);
        addEdge(graph, 2, 1, 2);
        addEdge(graph, 1, 3, 1);
        addEdge(graph, 2, 3, 5);
        addEdge(graph, 3, 4, 3);

        // Panggil algoritma Dijkstra dari simpul 0
        djikstra(graph, 0);
    }

    // Fungsi untuk menambahkan sisi ke graf
    static void addEdge(List<List<Edge>> g, int dari, int target, int w) {
        g.get(dari).add(new Edge(target, w));  // Tambahkan edge dari -> ke
        g.get(target).add(new Edge(dari, w));  // Karena graf tidak berarah, tambahkan juga sebaliknya
    }

    // Implementasi algoritma Dijkstra
    static void djikstra(List<List<Edge>> graph, int mulai) {
        int n = graph.size(); // Jumlah simpul
        int[] dist = new int[n]; // Array jarak minimum dari simpul awal
        int[] prev = new int[n]; // Array untuk melacak simpul sebelumnya (jalur)

        Arrays.fill(dist, Integer.MAX_VALUE); // Inisialisasi semua jarak sebagai tak hingga
        Arrays.fill(prev, -1); // Inisialisasi semua simpul sebelumnya sebagai -1 (belum ada)
        dist[mulai] = 0; // Jarak ke simpul awal adalah 0

        PriorityQueue<Node> pq = new PriorityQueue<>(); // Priority queue untuk memilih simpul dengan jarak minimum
        pq.add(new Node(mulai, 0)); // Masukkan simpul awal

        System.out.println("Memulai dari simpul: " + mulai);

        // Selama masih ada simpul di queue
        while (!pq.isEmpty()) {
            Node now = pq.poll(); // Ambil simpul dengan jarak terkecil
            int u = now.vertex;

            // Periksa semua tetangga dari simpul u
            for (Edge e : graph.get(u)) {
                int v = e.target; // Simpul tetangga
                int w = e.bobot;  // Bobot sisi u -> v

                // Jika jalur baru dari u ke v lebih pendek dari sebelumnya
                if(dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w; // Update jarak terpendek ke simpul v
                    prev[v] = u; // Simpan u sebagai simpul sebelumnya untuk v
                    pq.add(new Node(v, dist[v])); // Masukkan simpul v ke priority queue
                    System.out.println("Update: " + u + " -> " + v + " | jarak: " + dist[v]);
                }
            }
        }

        // Output akhir: jarak dan jalur dari simpul awal ke semua simpul
        System.out.println("\nJalur Terpendek dari simpul " + mulai + ":");
        for(int i = 0; i < n; i++) {
            System.out.println("Simpul " + i + 
                " | Jarak = " + dist[i] + 
                (prev[i] != -1 ? " | Dari = " + prev[i] : ""));
        }
    }
}
  