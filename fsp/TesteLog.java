class TesteLog{
    public static void main(String[] args) {
        String val = "-- line 1 col 23: invalid Escolha";
        EscritaLog escrita = new EscritaLog("log", ".txt");
        escrita.escrever(val);
    }
}