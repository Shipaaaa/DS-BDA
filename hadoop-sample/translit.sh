#!/bin/bash

WORD=$1

# sed-ом заменяем символы латиницы на символы кирилицы
TRS=$(echo "$WORD" | sed "y/abvgdeeziilmnoprufh/абвгдеёзийлмнопруфх/")
TRS=$(echo "$TRS"  | sed "y/ABVGDEEZIILMNOPRUFH/АБВГДЕЁЗИЙЛМНОПРУФХ/")

TRS=${TRS//оо/у};
TRS=${TRS//zh/ж};
TRS=${TRS//ch/ч};
TRS=${TRS//sh/ш};
TRS=${TRS//sch/щ};
TRS=${TRS//yu/ю};
TRS=${TRS//ya/я};
TRS=${TRS//tc/ц};
TRS=${TRS//s/с};
TRS=${TRS//k/к};
TRS=${TRS//K/к};
TRS=${TRS//кх/х};
TRS=${TRS//e/э};
TRS=${TRS//t/т};

echo "$TRS"