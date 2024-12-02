from gi.repository import Gio

all_apps = Gio.AppInfo.get_all() 

print("[")
for app in all_apps:
    print(app.get_display_name())
    print(", ")
print("]")
