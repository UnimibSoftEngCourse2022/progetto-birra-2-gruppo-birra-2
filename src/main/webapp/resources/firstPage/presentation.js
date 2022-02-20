class Presentation extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
        <style>

        .Presentation h1 {
            font-size: 70px;
            font-weight: 700;
            width: 650px;
            text-align: start;
            display: flex;
            align-items: center;
            letter-spacing: -0.03em;
        }

        .Presentation p {
            font-size: 18px;
            font-weight: 400;
            width: 470px;
            text-align: start;
            line-height: 27px;
            display: flex;
            align-items: center;
        }

        .Presentation .Button input {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 17px 40px;
            border-radius: 13px;
            font-size: 17px;
            font-weight: 700;
            cursor: pointer;
            text-align: center;
            border: none;
            color: white;
            background: black;
            transition: background 250ms ease-in-out, 
            transform 150ms ease;
            margin-top: 15px;
        }

        .Presentation .Button input:hover {
            background: #4E4F59;
        }

        .Presentation section {
            display: flex;
            margin-top: 30px;
        }

        .Presentation .Text {
            margin-bottom: 20px;
        }

        .Presentation {
            display: flex;
            flex-direction: row;
            align-items: center;
            align-content: center;
            justify-content: center;
            margin: calc(50vh - 140px - 300px) 0;
            gap: 200px;
        }

        .Presentation img {
            height: 603px;
            width: 546.97px;
        }

        .Presentation mark {
            color: #FFCC00;
            background: transparent;
        }

        @media screen and (max-height: 950px) {
            .Presentation {
                margin: 81px 240px;
            }
        }

        @media screen and (max-width: 1575px) {
            .Presentation img {
                display: none;
            }

            .Presentation div.Text {
                text-align: center;
            }

            .Presentation section {
                justify-content: center;
                justify-items: center;
            }

            .Presentation p, .Presentation h1 {
                text-align: center;
            }
        }

        @media screen and (max-width: 727px) {
            .Presentation h1 {
                font-size: 50px;
                width: 480px;
            }

            .Presentation p {
                font-size: 15px;
                width: 400px;
            }

            .Presentation .Button input {
                padding: 15px 30px;
                border-radius: 11px;
                font-size: 14px;
                margin-top: 7px;
            }

            .Presentation section {
                margin-top: 15px;
            }
        }

        </style>
            
        <div class="Presentation">
            <div class="Text">
                <section>
                    <h1>Salva le tue ricette e crea nuove Birre</h1>
                </section>
                
                <section>
                    <p>Crea nuove birre aggiungendo tutti i tuoi ingredienti e attrezzature oppure scopri cosa puoi produrre oggi.</p>
                </section>

                <section>
                    <form class="Button" action="${this.getAttribute('submitTo')}" method="GET">
                        <input type="submit" value="${this.getAttribute('value')}"/>
                    </form>
                </section>
            </div>

            <img src="${this.getAttribute('image')}"></img
        </div>
      `;
    }
  }
  
  customElements.define('main-presentation', Presentation);