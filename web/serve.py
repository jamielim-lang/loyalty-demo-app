import http.server
import os

os.chdir(os.path.dirname(os.path.abspath(__file__)))

handler = http.server.SimpleHTTPRequestHandler
httpd = http.server.HTTPServer(("", 3000), handler)
print("Serving Flybuys Web Preview on port 3000")
httpd.serve_forever()
