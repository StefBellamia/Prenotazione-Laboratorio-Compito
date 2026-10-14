package com.bellamia.servlet.prenotazionelaboratoriocompito;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;


@WebServlet("/PrenotazioneServlet")

public class PrenotazioneServlet extends HttpServlet {
    // ============================================================
    // STAMPA INTESTAZIONE DELLA PAGINA HTML
    // ============================================================
    private void stampaIntestazione(PrintWriter out, String titolo) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='it'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>" + titolo + "</title>");
        out.println("<style>");
        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("}");
        out.println(".menu-bar {");
        out.println("    background-color: #eee;");
        out.println("    padding: 5px;");
        out.println("    display: flex;");
        out.println("}");
        out.println(".menu {");
        out.println("    position: relative;");
        out.println("    margin-right: 20px;");
        out.println("    padding: 5px 10px;");
        out.println("    cursor: pointer;");
        out.println("}");
        out.println(".menu:hover {");
        out.println("    background-color: #ddd;");
        out.println("}");
        out.println(".submenu {");
        out.println("    display: none;");
        out.println("    position: absolute;");
        out.println("    top: 100%;");
        out.println("    left: 0;");
        out.println("    background-color: white;");
        out.println("    border: 1px solid #ccc;");
        out.println("    min-width: 150px;");
        out.println("    box-shadow: 2px 2px 5px rgba(0,0,0,0.2);");
        out.println("}");
        out.println(".submenu div {");
        out.println("    padding: 8px;");
        out.println("}");
        out.println(".submenu div:hover {");
        out.println("    background-color: #f0f0f0;");
        out.println("}");
        out.println(".menu:hover .submenu {");
        out.println("    display: block;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
    }


    // ============================================================
    // STAMPA MENU
    // ============================================================
    private void stampaMenu(PrintWriter out) {
        out.println("<body>");
        out.println("<div class='menu-bar'>");
        // FILE
        out.println("<div class='menu'>File");
        out.println("<div class='submenu'>");
        out.println(
                "<div onclick=\"location.href='index.html'\">" +
                        "Esci" +
                        "</div>"
        );
        out.println("</div>");
        out.println("</div>");
        // PRENOTAZIONE
        out.println("<div class='menu'>Prenotazione");
        out.println("<div class='submenu'>");
        out.println(
                "<div onclick=\"location.href='prenotazione.html'\">" +
                        "Nuova prenotazione" +
                        "</div>"
        );
        out.println("<div onclick=\"location.href='LeggiPrenotazioniServlet'\">Leggi prenotazioni</div>");
        out.println("<div onclick=\"location.href='ricercaPrenotazione.html'\">Ricerca prenotazioni</div>");
        out.println("<div onclick=\"location.href='ricercaPerMese.html'\">Ricerca Per Mese</div>");
        out.println("</div>");
        out.println("</div>");
        // AIUTO
        out.println("<div class='menu'>Aiuto");
        out.println("<div class='submenu'>");
        out.println("<div>Info</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
    }

    // ============================================================
    // STAMPA RIEPILOGO PRENOTAZIONE
    // ============================================================
    private void stampaRiepilogo(PrintWriter out,
                                 String nome,
                                 int ore,
                                 String postazione,
                                 double costoOrario,
                                 double sconto,
                                 String assistenza,
                                 double totale)
    {
        out.println("<h1>Riepilogo prenotazione</h1>");
        out.println("<p><strong>Studente:</strong> "+ nome + "</p>");
        out.println("<p><strong>Numero di ore:</strong> "+ ore + "</p>");
        out.println("<p><strong>Postazione:</strong> "+ postazione + "</p>");
        out.println("<p><strong>Costo orario:</strong> " + String.format("%.2f", costoOrario) + " €</p>");
        // Visualizzazione dello sconto
        if (sconto > 0) {
            out.println("<p><strong>Sconto 10%:</strong> " + String.format("%.2f", sconto) + " €</p>");
        }
        else {
            out.println("<p><strong>Sconto:</strong> " + "Non applicato</p>");
        }

        // Visualizzazione assistenza tecnica
        if (assistenza.equals("si")) {
            out.println("<p><strong>Assistenza tecnica:</strong> " + "Sì (+15 €)</p>");
        }
        else {
            out.println("<p><strong>Assistenza tecnica:</strong> No</p>");
        }

        out.println("<hr>");
        out.println("<h2>Totale da pagare: " + String.format("%.2f", totale) + " €</h2>");
    }


    // ============================================================
    // STAMPA PAGINA DI ERRORE
    // ============================================================
    private void stampaErrore(PrintWriter out, String messaggio) {
        out.println("<h1>Errore nella prenotazione</h1>");
        out.println("<p><strong>" + messaggio + "</strong></p>");
        out.println("<p>" + "<a href='prenotazione.html'>" + "Torna alla prenotazione" + "</a>" + "</p>");
    }

    // ============================================================
    // CHIUSURA PAGINA HTML
    // ============================================================
    private void stampaChiusura(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }


    // ============================================================
    // METODO doPost
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // --------------------------------------------------------
        // Preparazione della risposta
        // --------------------------------------------------------
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // --------------------------------------------------------
        // Acquisizione dei dati dal form
        // --------------------------------------------------------
        String nome = request.getParameter("nome");
        Integer giorno = Integer.valueOf(request.getParameter("giorno"));
        Integer mese = Integer.valueOf(request.getParameter("mese"));
        String oreStringa = request.getParameter("ore");
        String postazione = request.getParameter("postazione");
        String assistenza = request.getParameter("assistenza");

        // --------------------------------------------------------
        // VALIDAZIONE DEL NOME
        // --------------------------------------------------------
        if (nome == null || nome.trim().isEmpty()) {
            stampaMenu(out);
            stampaIntestazione(out, "Errore prenotazione");
            stampaErrore(out, "Il nome e cognome devono essere inseriti.");
            stampaChiusura(out);
            return;
        }

        // --------------------------------------------------------
        // VALIDAZIONE DELLA DATA
        // --------------------------------------------------------
        String data;
        String anno;
        if (mese==2 && giorno>28) {
            giorno = 28;
        }

        if ((mese == 4 || mese == 6 || mese == 9 || mese == 11) && giorno > 30) {
            giorno = 30;
        }

        if (mese>6) {
            anno="2026";
        } else {
            anno="2027";
        }

        data = giorno + "/" + mese + "/" + anno;


        // Elimina eventuali spazi prima e dopo il nome
        nome = nome.trim();
        // --------------------------------------------------------
        // VALIDAZIONE DEL NUMERO DI ORE
        // --------------------------------------------------------
        int ore;
        try {
            ore = Integer.parseInt(oreStringa);
        }
        catch (NumberFormatException e) {
            stampaIntestazione(out, "Errore prenotazione");
            stampaMenu(out);
            stampaErrore(out, "Il numero di ore deve essere un numero intero.");
            stampaChiusura(out);
            return;
        }

        // Controllo intervallo 1 - 8
        if (ore < 1 || ore > 8) {
            stampaIntestazione(out, "Errore prenotazione");
            stampaMenu(out);
            stampaErrore(out, "Il numero di ore deve essere compreso tra 1 e 8.");
            stampaChiusura(out);
            return;
        }

        // --------------------------------------------------------
        // GESTIONE ASSISTENZA
        // --------------------------------------------------------
        if (assistenza != null) {
            assistenza = "si";
        }
        else {
            assistenza = "no";
        }

        // --------------------------------------------------------
        // DETERMINAZIONE DEL COSTO ORARIO
        // --------------------------------------------------------
        double costoOrario;
        if (postazione.equals("standard")) {
            costoOrario = 5.0;
        }
        else if (postazione.equals("grafica")) {
            costoOrario = 8.0;
        }
        else {
            costoOrario = 10.0;
        }

        // --------------------------------------------------------
        // CALCOLO DEL COSTO
        // --------------------------------------------------------
        double totale = costoOrario * ore;

        // --------------------------------------------------------
        // APPLICAZIONE DELLO SCONTO DEL 10%
        // --------------------------------------------------------
        double sconto = 0;
        if (ore >= 5) {
            sconto = totale * 0.10;
            totale = totale - sconto;
        }

        // --------------------------------------------------------
        // AGGIUNTA DELL'ASSISTENZA TECNICA
        // --------------------------------------------------------
        if (assistenza.equals("si")) {
            totale = totale + 15;
        }

        // ========================================================
        // SALVATAGGIO SU FILE DI TESTO
        // ========================================================
        String record = nome + ";" +
                data + ";" +
                ore + ";" +
                postazione + ";" +
                assistenza + ";" +
                totale;

        String home = System.getProperty("user.home");

        File fileDiTesto = new File(home + "/Documents/prenotazioni.txt");
        FileWriter fw = new FileWriter(fileDiTesto, true);
        PrintWriter file = new PrintWriter(fw);
        file.println(record);
        file.close();


        // ========================================================
        // SALVATAGGIO SU FILE BINARIO
        // ========================================================
        /*File fileBinario = new File(home + "/Documents/prenotazioni.dat");
        FileOutputStream fos = new FileOutputStream(fileBinario, true);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);

        // Scrittura dei campi secondo il loro tipo
        dos.writeUTF(nome);
        dos.writeInt(ore);
        dos.writeUTF(postazione);
        dos.writeBoolean(assistenza.equals("si"));
        dos.writeDouble(totale);
        dos.close();*/

        // ========================================================
        // VISUALIZZAZIONE DELLA RISPOSTA
        // ========================================================
        stampaIntestazione(out, "Gestione Prenotazioni del Laboratorio di Informatica");
        stampaMenu(out);
        stampaRiepilogo(out, nome, ore, postazione, costoOrario, sconto, assistenza, totale);
        stampaChiusura(out);
    }
}
