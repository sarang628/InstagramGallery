git pull

git submodule update --recursive --remote

git submodule sync

git submodule foreach '
    echo "==> 처리 중: $name"
    git fetch origin
    if git rev-parse --verify origin/main > /dev/null 2>&1; then
        git checkout main || git checkout -b main origin/main
        git reset --hard origin/main
    else
        echo "❌ 서브모듈 [$name]에는 main 브랜치가 없습니다."
    fi
'