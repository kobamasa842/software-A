# software-A

## 動かし方
### サーバー
- コマンドラインから起動する場合
  - `./dev_program_DB/mydb` に移動してから `java -classpath ../lib/hsqldb.jar org.hsqldb.Server -database mydb`

### クライアント
- コマンドラインから起動する場合
  - `javac ./Waseda-SE/src/app/cui/CUI.java -encoding sjis -sourcepath ./Waseda-SE/src -d ./Waseda-SE/out` でコンパイル
  - `java -classpath ./Waseda-SE/lib/hsqldb.jar:./Waseda-SE/out app.cui.CUI` で実行
