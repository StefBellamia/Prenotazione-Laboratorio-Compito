package com.bellamia.servlet.prenotazionelaboratoriocompito;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


@WebServlet("/RicercaPerMeseServlet")

public class RicercaPerMese extends HttpServlet
{


    // ============================================================
    // STAMPA INTESTAZIONE DELLA PAGINA HTML
    // ============================================================
    private void stampaIntestazione(PrintWriter out, String titolo)
    {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='it'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>" + titolo + "</title>");

        out.println("<style>");

        // --------------------------------------------------------
        // STILE DELLA PAGINA
        // --------------------------------------------------------
        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("}");


        // --------------------------------------------------------
        // STILE DEL MENU
        // --------------------------------------------------------
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
        out.println("    min-width: 180px;");
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


        // --------------------------------------------------------
        // STILE DEL TITOLO
        // --------------------------------------------------------
        out.println("h1 {");
        out.println("    text-align: center;");
        out.println("    margin-top: 30px;");
        out.println("}");


        // --------------------------------------------------------
        // STILE DELLA TABELLA
        // --------------------------------------------------------
        out.println("table {");
        out.println("    width: 90%;");
        out.println("    border-collapse: collapse;");
        out.println("    margin: 30px auto;");
        out.println("}");

        out.println("th {");
        out.println("    background-color: #eee;");
        out.println("    padding: 12px;");
        out.println("    border: 1px solid #ccc;");
        out.println("    text-align: left;");
        out.println("}");

        out.println("td {");
        out.println("    padding: 10px;");
        out.println("    border: 1px solid #ccc;");
        out.println("}");

        out.println("tr:hover {");
        out.println("    background-color: #f5f5f5;");
        out.println("}");


        // --------------------------------------------------------
        // STILE DEL MESSAGGIO
        // --------------------------------------------------------
        out.println(".messaggio {");
        out.println("    width: 90%;");
        out.println("    margin: 30px auto;");
        out.println("    text-align: center;");
        out.println("}");


        out.println("</style>");

        out.println("</head>");
    }


    // ============================================================
    // STAMPA MENU
    // ============================================================
    private void stampaMenu(PrintWriter out)
    {
        out.println("<body>");

        out.println("<div class='menu-bar'>");


        // --------------------------------------------------------
        // FILE
        // --------------------------------------------------------
        out.println("<div class='menu'>File");

        out.println("<div class='submenu'>");

        out.println(
                "<div onclick=\"location.href='index.html'\">" +
                        "Esci" +
                        "</div>"
        );

        out.println("</div>");

        out.println("</div>");


        // --------------------------------------------------------
        // PRENOTAZIONE
        // --------------------------------------------------------
        out.println("<div class='menu'>Prenotazione");

        out.println("<div class='submenu'>");

        out.println(
                "<div onclick=\"location.href='prenotazione.html'\">" +
                        "Nuova prenotazione" +
                        "</div>"
        );

        out.println(
                "<div onclick=\"location.href='LeggiPrenotazioniServlet'\">" +
                        "Leggi prenotazioni" +
                        "</div>"
        );
        out.println("<div onclick=\"location.href='ricercaPrenotazione.html'\">Ricerca prenotazioni</div>");
        out.println("<div onclick=\"location.href='ricercaPerMese.html'\">Ricerca Per Mese</div>");

        out.println("</div>");

        out.println("</div>");


        // --------------------------------------------------------
        // AIUTO
        // --------------------------------------------------------
        out.println("<div class='menu'>Aiuto");

        out.println("<div class='submenu'>");

        out.println("<div>Info</div>");

        out.println("</div>");

        out.println("</div>");


        out.println("</div>");
    }


    // ============================================================
    // STAMPA ELENCO PRENOTAZIONI
    // ============================================================
    private void ricercaPrenotazioni(PrintWriter out, String dataRichiesta)
    {
        out.println("<h1>Elenco prenotazioni</h1>");


        // --------------------------------------------------------
        // INDIVIDUAZIONE DEL FILE
        // --------------------------------------------------------
        String home = System.getProperty("user.home");

        File fileDiTesto =
                new File(home + "/Documents/prenotazioni.txt");


        // --------------------------------------------------------
        // CONTROLLO ESISTENZA DEL FILE
        // --------------------------------------------------------
        if (!fileDiTesto.exists())
        {
            out.println(
                    "<div class='messaggio'>" +
                            "Nessuna prenotazione presente." +
                            "</div>"
            );

            return;
        }


        // --------------------------------------------------------
        // LETTURA DEL FILE DI TESTO
        // --------------------------------------------------------
        try
        {
            FileReader fr = new FileReader(fileDiTesto);

            BufferedReader br = new BufferedReader(fr);

            boolean trovato = false;

            // ----------------------------------------------------
            // CREAZIONE DELLA TABELLA
            // ----------------------------------------------------
            out.println("<table>");


            // ----------------------------------------------------
            // INTESTAZIONE DELLA TABELLA
            // ----------------------------------------------------
            out.println("<tr>");

            out.println("<th>Nome e cognome</th>");

            out.println("<th>Data Prenotazione</th>");

            out.println("<th>Ore</th>");

            out.println("<th>Postazione</th>");

            out.println("<th>Assistenza</th>");

            out.println("<th>Totale</th>");

            out.println("</tr>");

            // ----------------------------------------------------
            // LETTURA DEI RECORD
            // ----------------------------------------------------
            String record;

            while ((record = br.readLine()) != null)
            {
                // ------------------------------------------------
                // SEPARAZIONE DEI CAMPI DEL RECORD
                // ------------------------------------------------
                String[] campi = record.split(";");


                // ------------------------------------------------
                // ACQUISIZIONE DEI SINGOLI CAMPI
                // ------------------------------------------------

                String data = campi[1];

                if (data.equalsIgnoreCase(dataRichiesta)) {

                    trovato = true;

                    String nome = campi[0];

                    String ore = campi[2];

                    String postazione = campi[3];

                    String assistenza = campi[4];

                    String totaleStringa = campi[5];


                    // ------------------------------------------------
                    // FORMATTAZIONE DEL TIPO DI POSTAZIONE
                    // ------------------------------------------------
                    if (postazione.equals("standard"))
                    {
                        postazione = "Standard";
                    }
                    else if (postazione.equals("grafica"))
                    {
                        postazione = "Grafica";
                    }
                    else if (postazione.equals("sviluppo"))
                    {
                        postazione = "Sviluppo";
                    }


                    // ------------------------------------------------
                    // FORMATTAZIONE DELL'ASSISTENZA
                    // ------------------------------------------------
                    if (assistenza.equals("si"))
                    {
                        assistenza = "Sì";
                    }
                    else
                    {
                        assistenza = "No";
                    }


                    // ------------------------------------------------
                    // FORMATTAZIONE DEL TOTALE
                    // ------------------------------------------------
                    double totale =
                            Double.parseDouble(totaleStringa);


                    // ------------------------------------------------
                    // VISUALIZZAZIONE DEL RECORD
                    // ------------------------------------------------
                    out.println("<tr>");

                    out.println(
                            "<td>" +
                                    nome +
                                    "</td>"
                    );

                    out.println("<td>" + dataRichiesta + "</td>");

                    out.println("<td>" + ore + "</td>");

                    out.println("<td>" + postazione + "</td>"
                    );

                    out.println(
                            "<td>" +
                                    assistenza +
                                    "</td>"
                    );

                    out.println(
                            "<td>" +
                                    String.format("%.2f", totale) +
                                    " €</td>"
                    );

                    out.println("</tr>");



                }

            }

            // ----------------------------------------------------
            // CHIUSURA DEL FILE
            // ----------------------------------------------------
            br.close();

            if (trovato == false) {

                out.println("<tr>");

                out.println("<td colspan=\"5\" style=\"text-align: center;\">");
                out.println(
                        "<div class='messaggio'>" +
                                "Non è stata trovata nessuna prenotazione nella data: " + dataRichiesta + "." +
                                "</div>"
                );
                out.println("</td>");

                out.println("</tr>");
            }

            // ----------------------------------------------------
            // CHIUSURA DELLA TABELLA
            // ----------------------------------------------------
            out.println("</table>");

        }
        catch (IOException e)
        {
            out.println(
                    "<div class='messaggio'>" +
                            "Errore durante la lettura del file: " +
                            e.getMessage() +
                            "</div>"
            );
        }
        catch (NumberFormatException e)
        {
            out.println(
                    "<div class='messaggio'>" +
                            "Errore nel formato dei dati presenti nel file." +
                            "</div>"
            );
        }
    }


    // ============================================================
    // CHIUSURA PAGINA HTML
    // ============================================================
    private void stampaChiusura(PrintWriter out)
    {
        out.println("</body>");

        out.println("</html>");
    }


    // ============================================================
    // METODO doGet
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException
    {

        // --------------------------------------------------------
        // PREPARAZIONE DELLA RISPOSTA
        // --------------------------------------------------------
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        Integer giorno = Integer.valueOf(request.getParameter("giorno"));
        Integer mese = Integer.valueOf(request.getParameter("mese"));

        // --------------------------------------------------------
        // VALIDAZIONE DELLA DATA
        // --------------------------------------------------------
        String dataRichiesta;
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

        dataRichiesta = giorno + "/" + mese + "/" + anno;

        // --------------------------------------------------------
        // GENERAZIONE DELLA PAGINA HTML
        // --------------------------------------------------------
        stampaIntestazione(
                out,
                "Ricerca prenotazione del Laboratorio di Informatica"
        );


        stampaMenu(out);

        ricercaPrenotazioni(out, dataRichiesta);

        stampaChiusura(out);
    }
}
