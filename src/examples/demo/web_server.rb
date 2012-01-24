# Copyright 2011 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

require "vertx"
include Vertx

@server = HttpServer.new
sjs_server = SockJSServer.new(@server)

# Bridge the vert.x event bus to the client side
sjs_server.install_app({"prefix" => "/eventbus"}, SockJSBridgeHandler.new)

# Also serve the index page
@server.request_handler do |req|
  puts "uri is #{req.uri}"
  if req.uri == '/'
    puts 'sending index.html'
    req.response.send_file("web/index.html")
  else
    req.response.send_file("web" + req.uri) if !req.uri.include? '..'
  end
end

@server.listen(8080)

def vertx_stop
  @server.close
end