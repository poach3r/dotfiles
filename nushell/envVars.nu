$env.JAVA_HOME = '/home/poacher/.jdks/temurin-22.0.2/'
$env.PATH = ($env.PATH | split row (char esep)
    #| prepend '/home/poacher/.local/share/coursier/bin/'
    | prepend '/home/poacher/.local/bin/'
    | prepend '/home/poacher/.cargo/bin/')
