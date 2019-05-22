## Простой Guu интерпретатор и пошаговый отладчик
Программа на `Guu` состоит из набора процедур. 
Каждая процедура начинается со строки `sub <subname>` и завершается объявлением другой процедуры 
(или концом файла, если процедура в файле последняя). 
Исполнение начинается с `sub main`.

Тело процедуры – набор инструкций, каждая из которых находится на отдельной строке. 
В начале строки могут встречаться незначимые символы табуляции или пробелы. 
Пустые строки игнорируются. Комментариев в Guu нет.

В Guu есть лишь три оператора:
 - `set <varname> <new value>` – задание нового целочисленного значения переменной.
  - `call <subname>` – вызов процедуры. Вызовы могут быть рекурсивными. 
  - `print <varname>` – печать значения переменной на экран.

Переменные в Guu имеют глобальную область видимости. 
Переменные должны быть явно ранее инициализированны в программе перед их печатью оператором `print`

###Использование
Guu интерпретатор принимает на вход имя файла, в котором написан код на `Guu` и опцию `-debug`
если нужно запустить пошаговый отладчик.
Перед исполнением кода, проверяется, что все функции используемые 
вызываемые транзитивно из функции `main`, и что все переменные явно инициализированны перед их выводом.

При включении опции `-debug` интерпретатор работает в режиме пошагового отладчика.
Команды, используемые отладчиком:
 - `i` – step into, отладчик заходит внутрь call <subname>.
 - `o` – step over, отладчик не заходит внутрь call.
 - `trace` – печать stack trace исполнения с номерами строк, начиная с main..
 - `var` – печать значений всех объявлённых переменных.
 - `cintinue` - продолжить исполнение программы без отладки
 - `exit` - завершить исполнение программы
 
###Сборка и запуск
Чтобы запустить программу используйте `maven install`. Собранное решение находится в `./target/JB_Internship-[VERSION]-jar-with-dependencies.jar`.