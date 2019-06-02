inotifywait -e close_write,moved_to,create -m . |
while read -r directory events filename; do
  if [ "$filename" = "F1-0.0.1.jar"] || [ "$filename" = "F1.db" ]; then
    ./restartAPI.sh
  fi
done
