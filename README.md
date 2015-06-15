# FUCoin Transactions with GUI

* Important: Download akka Framework and use it as library

We want to implement a Bitcoin variant: FUcoin - a decentralized payment system! Research
all information you can get about the inner workings of Bitcoin. Only stop if it is clear to
you how it could be implemented.
This includes (not exclusively):
# Block chain
Die Block Chain (dt. ‚Blockkette‘) ist das Journal, in dem alle Bitcoin-Transaktionen verzeichnet werden. Sie besteht aus einer Reihe von Datenblöcken, in denen jeweils eine oder mehrere Transaktionen zusammengefasst und mit einer Prüfsumme versehen sind. Neue Blöcke werden in einem rechenintensiven Prozess erschaffen, der sich Mining nennt, und anschließend über das Netzwerk an die Teilnehmer verbreitet.

Eine Blockchain ist eine Datenbank mit Transaktionen, die über alle Nodes verteilt ist, die am auf dem Bitcoin-Protokoll basierenden System teilnehmen. Eine komplette Kopie der aktuellen Blockain enthält jede Transaktion, die jemals bis zum aktuellen Zeitpunkt an ausgeführt wurde.

Jeder Block enthält einen Hash des vorhergehenden Blocks. Das hat eine Kette von Blöcken vom Genesis Block bis zum aktuellen Block zur Folge. Es ist sichergestellt, das jeder Block zeitlich nach dem vorhergehenden Block kommt, weil der Hash des vorhergehenden Blocks sonst nicht bekannt wäre. Jeder Block ist außerdem aufgrund der verwendeten Algorithmen nicht nachträglich änderbar sobald er sich einige Blöcke lang in der Blockchain befindet, da jeder Folgeblock ebenfalls neu berechnet werden müsste. Da sich die in Bitcoin verwendeten Algorithmen der Gesamtrechenleistung aller Nodes anpassen ist dies absolut unwahrscheinlich bis unmöglich. Dadurch wird das Double Spending, das doppelte Ausgeben von Bitcoins sehr schwierig. Die Blockchain ist die Hauptinnovation an Bitcoin.
Ehrliche Erzeuger bauen nur auf einem Block auf wenn er der letzte Block in der längsten, gültigen Kette von Blöcken ist.

Für jeden Block in der Blockchain gibt es nur einen Pfad zum Genesis Block. Ausgehend vom Genesis Block kann es allerdings Abzweigungen geben. Abzweigungen mit nur einem Block werden von Zeit zu Zeit erzeugt, wenn zwei Blöcke nur einige Sekunden kurz hintereinander erzeugt werden. Wenn das passiert bilden Nodes ihre Blöcke auf dem Block, den sie zuerst bekommen haben. Alle Folgeblöcke werden wieder an den Block gehangen, der in der längsten Kette hängt.
Blöcke in kürzeren (oder ungültigen) Ketten nennen sich "Orphan Blocks". Diese werden zwar in der Blockchain gespeichert, aber für nichts weiter benutzt. Wenn ein Block ein Orphan Block wird werden alle seine gültigen Transaktionen wieder in den Pool unabgearbeiteter Transaktionen hinzugefügt und werden in einem der späteren Blöcke eingebettet. Die Belohnung für einen erzeugten Orphan Block verfällt. Das ist auch der Grund weswegen das Netzwerk eine Wartezeit von 100 Blöcken verlangt bevor die Belohnung für einen erzeugten Block ausgezahlt wird.

# Unit limit
The unit of account of the bitcoin system is bitcoin. As of 2014, symbols used to represent bitcoin are BTC, XBT. Small amounts of bitcoin used as alternative units are millibitcoin (mBTC), microbitcoin (µBTC), and satoshi. Named in homage to bitcoin's creator, a satoshi is the smallest amount within bitcoin representing 0.00000001 bitcoin, one hundred millionth of a bitcoin. A millibitcoin equals to 0.001 bitcoin, which is one thousandth of bitcoin. One microbitcoin equals to 0.000001 bitcoin, which is one millionth of bitcoin. A microbitcoin is sometimes referred to as a bit.

# Ownership of money
Ownership of bitcoins implies that a user can spend bitcoins associated with a specific address. To do so, a payer must digitally sign the transaction using the corresponding private key. Without knowledge of the private key the transaction cannot be signed and bitcoins cannot be spent. The network verifies the signature using the public key. If the private key is lost, the bitcoin network will not recognize any other evidence of ownership; the coins are then unusable, and thus effectively lost. For example, in 2013 one user said he lost 7,500 bitcoins, worth $7.5 million at the time, when he discarded a hard drive containing his private key.

# Transactions
A transaction must have one or more inputs. For the transaction to be valid, every input must be an unspent output of a previous transaction. Every input must be digitally signed. The use of multiple inputs corresponds to the use of multiple coins in a cash transaction. A transaction can also have multiple outputs, allowing one to make multiple payments in one go. A transaction output can be specified as an arbitrary multiple of satoshi. Similarly as in a cash transaction, the sum of inputs (coins used to pay) can exceed the intended sum of payments. In such case, an additional output is used, returning the change back to the payer. Any input satoshis not accounted for in the transaction outputs become the transaction fee.
To send money to a bitcoin address, users can click links on webpages; this is accomplished with a provisional bitcoin URI scheme using a template registered with IANA. Bitcoin clients like Electrum and Armory support bitcoin URIs. Mobile clients recognize bitcoin URIs in QR codes, so that the user does not have to type the bitcoin address and amount in manually. The QR code is generated from the user input based on the payment amount. The QR code is displayed on the mobile device screen and can be scanned by a second mobile device.


Die Überweisung von Bitcoins zwischen den Teilnehmern wird in sog. Transaktionen abgewickelt, die für den Benutzer praktisch so funktionieren, wie eine Banküberweisung. Der Zahlungssender muss lediglich die Bitcoin-Adresse (vergleichbar mit der Kontonummer bzw. IBAN) des Zahlungsempfängers kennen, um ihm einen Betrag überweisen zu können. Eine Bestätigung durch den Empfänger ist nicht nötig. Die Bitcoin-Adressen können von der Bitcoin Core-Software bei Bedarf generiert werden.
Der Zahlungsempfänger muss für den Empfang der Zahlung nicht mit dem Netzwerk verbunden sein. Der Sender muss sich nur kurz verbinden, um die Transaktion abzusetzen.
Eine Rückabwicklung von Transaktionen ist, nachdem sie einmal abgeschickt wurden, ausgeschlossen. Auch das Einziehen von Guthaben von einem Konto, wie beim Lastschriftverfahren, ist nicht möglich.

## Ablauf der Transaktion im Detail
Genaugenommen existieren im Bitcoin-System keine Konten, die ein Guthaben aufweisen können. Das „Guthaben“, das der Bitcoin Core oder andere Wallet-Programme ausweisen, sind eingegangene Gutschriften auf die Bitcoin-Adressen aus der Wallet des Benutzers, die noch nicht weiterüberwiesen wurden (sog. unspent transaction outputs).
Jede Transaktion enthält mindestens eine Adresse als Eingabe, mindestens eine Adresse als Ausgabe, für jede der Empfängeradressen den entsprechenden Betrag und noch weitere Felder für die Signatur und Verwaltung. Der Betrag wird den Eingabeadressen entnommen und den Zieladressen in der angegebenen Höhe gutgeschrieben. In einer Transaktion können auch mehrere einzelne Überweisungen zusammengefasst werden. Guthaben kann von mehreren Adressen zusammengeführt und unter mehreren Adressen aufgeteilt werden. Die Beträge werden von den sendenden Adressen immer vollständig abgezogen. Verbleibt „Wechselgeld” wird es einer Adresse des bisherigen Besitzers wieder gutgeschrieben. Es ist auch möglich, eine Überweisung von mehreren Teilnehmern signieren zu lassen (z. B. bei einem Treuhanddienst).
Abschließend wird die gesamte Transaktion mit dem privaten Schlüssel des Senders signiert, was sie damit authentisiert und vor Veränderungen schützt. Danach wird die Transaktion ins Peer-to-Peer-Netzwerk übertragen und mit einem Flooding-Algorithmus verbreitet. Der Absender schickt seine Transaktion an alle ihm bekannten Bitcoin Cores im Netzwerk. Diese verifizieren die Signatur und prüfen, ob die Transaktion gültig ist. Anschließend leiten sie die Transaktion an die ihnen bekannten Bitcoin Cores weiter. Dies wiederholt sich, bis die Transaktion allen Bitcoin Cores im Netzwerk bekannt ist. Sobald genügend Bitcoin Cores die Transaktion bekannt ist, beginnen diese sie zu verarbeiten, indem sie durch Mining einen Block erzeugen, in dem die Transaktion enthalten ist.

# Mining
Durch das Mining werden neue Blöcke erzeugt und anschließend zur Blockkette hinzugefügt. Durch neue Blöcke werden neue Bitcoins ausgegeben und gleichzeitig ein Teil der neuen oder noch offenen Transaktionen bestätigt. Bis 2013 wurden 50, ab 2014 werden 25 Bitcoins mit jedem neuem Block ausgezahlt. Auf diese Weise findet eine dezentrale Geldschöpfung statt. Der Vorgang ist sehr rechenintensiv und im Gegenzug erhält der Teilnehmer, der einen gültigen Block erzeugt, als Belohnung die geschöpften Bitcoins und die Gebühren aus den enthaltenen Transaktionen. Nachdem ein neuer gültiger Block gefunden wurde, wird er, wie unbestätigte Transaktionen, per Flooding-Algorithmus an alle Bitcoin Cores im Netzwerk als neue längere gültige Blockkette verbreitet. Das Mining im Bitcoin-System löst auf diese Weise auch das Problem der byzantinischen Generäle: Da es keine zentrale Instanz gibt, welche die Teilnehmer beglaubigt, vertrauen sich die Bitcoin Cores prinzipbedingt gegenseitig nicht. Das Problem besteht für jeden Bitcoin Core darin, herauszufinden, welche Blöcke bzw. welche Blockkette nun die „richtige“ ist, d. h. welcher die Mehrheit vertraut. Gültige Blöcke werden nur durch das rechenintensive Mining erschaffen. So vertraut jeder Bitcoin Core der längsten gültigen Blockkette, da hinter dieser die meiste Rechenleistung steht und deswegen auch die Mehrheit der Teilnehmer vermutet wird.

# Implementation
You can take common techniques as granted, i.e. you don’t have to explain how SHA-256 or
public-key cryptography works.
Please use Akka to implement a virtual wallet, which can hold a positive amount of cash.

In der Aufgabe soll neben der Recherchearbeit auch eine Virtual Wallet implementiert werden, jedoch noch keine echte Block Chain. Da der Kern der Wallet alleine eigentlich wenig Informationen beinhaltet, wäre dies sehr schnell implementiert. Bitte macht euch zusätzlich Gedanken über benötigte Funktionen, Schnittstellen und Eingabemöglichkeiten und implementiert diese, sodass wir später auf sie zurückgreifen können.

Benötigte Funktionalitäten sind beispielsweise:

* Erstellen einer Wallet
* Guthabenabfrage
* Übweisen von Coins
* etc.
