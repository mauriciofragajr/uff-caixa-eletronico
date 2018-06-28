<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="data:image/jpeg;base64,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">

        <title>Home</title>

        <!-- Bootstrap core CSS -->
        <link href="http://getbootstrap.com/docs/4.1/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            /*
     * Globals
     */

            /* Links */
            a,
            a:focus,
            a:hover {
                color: #fff;
            }

            /* Custom default button */
            .btn-secondary,
            .btn-secondary:hover,
            .btn-secondary:focus {
                color: #333;
                text-shadow: none; /* Prevent inheritance from `body` */
                background-color: #fff;
                border: .05rem solid #fff;
            }
            .btn {
                width: 200px;
                margin: 10px;
            }

            /*
             * Base structure
             */

            html,
            body {
                height: 100%;
                background-color: #008080;
            }

            body {
                display: -ms-flexbox;
                display: flex;
                color: #fff;
                text-shadow: 0 .05rem .1rem rgba(0, 0, 0, .5);
                box-shadow: inset 0 0 5rem rgba(0, 0, 0, .5);
            }

            .cover-container {
                max-width: 42em;
            }


            /*
             * Header
             */
            .masthead {
                margin-bottom: 2rem;
            }

            .masthead-brand {
                margin-bottom: 0;
            }

            .nav-masthead .nav-link {
                padding: .25rem 0;
                font-weight: 700;
                color: rgba(255, 255, 255, .5);
                background-color: transparent;
                border-bottom: .25rem solid transparent;
            }

            .nav-masthead .nav-link:hover,
            .nav-masthead .nav-link:focus {
                border-bottom-color: rgba(255, 255, 255, .25);
            }

            .nav-masthead .nav-link + .nav-link {
                margin-left: 1rem;
            }

            .nav-masthead .active {
                color: #fff;
                border-bottom-color: #fff;
            }

            @media (min-width: 48em) {
                .masthead-brand {
                    float: left;
                }
                .nav-masthead {
                    float: right;
                }
            }

            /*
             * Cover
             */
            .cover {
                padding: 0 1.5rem;
            }
            .cover .btn-lg {
                padding: .75rem 1.25rem;
                font-weight: 700;
            }

            /*
             * Footer
             */
            .mastfoot {
                color: rgba(255, 255, 255, .5);
            }
        </style>
    </head>

    <body class="text-center">

        <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
            <jsp:include page="menu/partials/header.jsp" />

            <main role="main" class="inner cover">
                <h1 class="cover-heading">O que deseja fazer?</h1>
                <p class="lead">
                    <a href="/CaixaEletronico/Saldo" class="btn btn-lg btn-secondary">Saldo</a>
                    <a href="/CaixaEletronico/Extrato" class="btn btn-lg btn-secondary">Extrato</a>
                    <a href="/CaixaEletronico/Saque" class="btn btn-lg btn-secondary">Saque</a>
                    <a href="/CaixaEletronico/Deposito" class="btn btn-lg btn-secondary">Depósito</a>
                    <a href="/CaixaEletronico/Transferencia" class="btn btn-lg btn-secondary">Transferência</a>
                </p>
            </main>

            <footer class="mastfoot mt-auto">
                <div class="inner">
                    <p><b>Banco - </b><%= new java.util.Date()%></p>
                </div>
            </footer>
        </div>


    </body>
</html>

