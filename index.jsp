<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Prenotazioni del Laboratorio di Informatica</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .menu-bar {
            background-color: #eee;  /* barra del menu colore grigio chiaro */
            padding: 5px;
            display: flex;   /* flex indica che gli elementi sono in riga */
        }

        /*.menu sono le voci del menu */
        .menu {
            position: relative;    /* indica che iul sottomenu si collecherà rispetto a questo elemento */
            margin-right: 20px;
            padding: 5px 10px; /* aumenta area hover */
            cursor: pointer;
        }

        .menu:hover {
            background-color: #ddd; /* quando passi il mouse il menù cambia colore */
        }

        .submenu {
            display: none;  /* Trucco: i menu a tendina non si vedono */
            position: absolute;
            top: 100%; /* 🔥 CORREZIONE FONDAMENTALE */
            left: 0;
            background-color: #fff;
            border: 1px solid #ccc;
            min-width: 150px;
            box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
        }

        /* prendi tutti i <div> che si trovano dentro un elemento con classe submenu */
        /* e applica loro uno stile*/
        .submenu div {
            padding: 8px;
        }

        /* Quando passi il mouse sopra un <div> dentro .submenu, */
        /* cambia il suo colore di sfondo. */
        .submenu div:hover {
            background-color: #f0f0f0;
        }

        /* Quando il mouse è sopra un elemento con classe menu, */
        /* mostra il suo submenu */

        .menu:hover .submenu {
            display: block;
        }
    </style>
</head>
<body>
<!--- menu-bar è la barra principale del menu: -->
<!--- dentro ci sono tre blocchi -->
<div class="menu-bar">
    <div class="menu">Prenotazione
        <div class="submenu">
            <div onclick="location.href='prenotazione.html'">   Nuova Prenotazione </div>
            <div onclick="location.href='LeggiPrenotazioniServlet'">Leggi Prenotazioni</div>
            <div onclick="location.href='ricercaPrenotazione.html'">Ricerca Prenotazioni</div>
            <div onclick="location.href='ricercaPerMese.html'">Ricerca Per Mese</div>

        </div>
    </div>

    <div class="menu">Modifica
        <div class="submenu">
            <div>Taglia</div>
            <div>Copia</div>
            <div>Incolla</div>
        </div>
    </div>

    <div class="menu">Aiuto
        <div class="submenu">
            <div>Info</div>
        </div>
    </div>
</div>

</body>
</html>