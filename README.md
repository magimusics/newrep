# stringsHandler

Эта небольшая программа необходима для обработки файлов, в которых строки имеют следующий вид:

111;222;223<br>
232;754;433<br>
111;434;985<br>
847;222;455<br>

<p>И так около миллиона строчек. Необходимо распределить строчки в группу, если есть совпадения по не пустым значениям в определенных столбцах. Причем если есть пересечения совпадений, то строчки принадлежат одной группе.</p>
<p>Так как миллион строчек - довольно немаленькое количество, то пришлось придумать этот алгоритм, чтобы сократить время обработки.</p>
